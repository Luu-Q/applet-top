package com.applet.service.area;

import com.applet.dao.area.CityMapper;
import com.applet.entity.result.ResultModel;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AreaService {

    @Autowired
    CityMapper cityMapper;

    public ResultModel areaList(String areaType, Integer parentId) {
        int type = -1;
        if ("sheng".equals(areaType)) {
            type = 1;
        } else if ("shi".equals(areaType)) {
            type = 2;
        } else if ("qu".equals(areaType)) {
            type = 3;
        }
        List<Map<String, Object>> maps = cityMapper.queryCityByTypeAndParentId(type, parentId);
        return ResultModel.succWithData(maps);
    }

    public String getCityNameByCode(String code) {
        String name = cityMapper.getCityNameByCode(code);
        if (StringUtils.isEmpty(name)) name = StringUtils.EMPTY;
        return name;
    }

    public List<Map<String, Object>> areaCityAll() {
        List<Map<String, Object>> mapList = cityMapper.queryCity();
        List<Map<String, Object>> listParentRecord = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> listNotParentRecord = new ArrayList<Map<String, Object>>();
        mapList.stream().forEach(city->{
            int type = (int) city.get("type");
            Map<String, Object> map = Maps.newHashMap();
            map.put("code",city.get("code").toString());
            map.put("name",city.get("name").toString());
            map.put("cityId",city.get("id").toString());
            map.put("parentId",city.get("parent_id").toString());
            if(type==1){
                listParentRecord.add(map);
            }else {
                listNotParentRecord.add(map);
            }
        });
        if (listParentRecord.size() > 0) {
            listParentRecord.stream().forEach(record->{
                record.put("childs", this.getTreeChildRecord(listNotParentRecord, record.get("cityId").toString()));
            });
        }
        return listParentRecord;
    }

    private List<Map<String, Object>> getTreeChildRecord(List<Map<String, Object>> childList, String parentId) {
        List<Map<String, Object>> listParentRecord = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> listNotParentRecord = new ArrayList<Map<String, Object>>();
        // 遍历tmpList，找出所有的根节点和非根节点
        if (childList != null && childList.size() > 0) {
            childList.stream().forEach(record->{
                // 对比找出父节点
                if (record.get("parentId").toString().equals(parentId)) {
                    listParentRecord.add(record);
                } else {
                    listNotParentRecord.add(record);
                }
            });
        }
        // 查询子节点
        if (listParentRecord.size() > 0) {
            listParentRecord.stream().forEach(record->{
                // 递归查询子节点
                record.put("childs",getTreeChildRecord(listNotParentRecord, record.get("cityId").toString()));
            });
        }
        return listParentRecord;
    }

}

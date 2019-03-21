package com.applet.dao.area;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface CityMapper {

    @Select("SELECT id AS cityId,code,name,type FROM city WHERE type=#{type} AND parent_id = #{parentId} AND status=1 ORDER BY sort ASC")
    List<Map<String, Object>> queryCityByTypeAndParentId(@Param("type") Integer type, @Param("parentId") Integer parentId);

    @Select("SELECT name FROM ylt_city WHERE code=#{code}")
    String getCityNameByCode(@Param("code") String code);

    @Select("SELECT id,code,name,type,parent_id FROM city WHERE type in (1,2,3) AND status=1 ")
    List<Map<String, Object>> queryCity();
}

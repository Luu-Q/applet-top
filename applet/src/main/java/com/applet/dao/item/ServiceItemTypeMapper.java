package com.applet.dao.item;

import com.applet.model.item.ServiceItemType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ServiceItemTypeMapper {

    int insert(ServiceItemType record);

    int update(ServiceItemType record);

    ServiceItemType selectByKey(Integer id);

    @Select("SELECT * FROM service_item_type WHERE `status` = 1")
    List<ServiceItemType> queryServiceItemTypeList();
}
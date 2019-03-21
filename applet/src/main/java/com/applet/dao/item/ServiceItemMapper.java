package com.applet.dao.item;

import com.applet.model.item.ServiceItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ServiceItemMapper {

    int insert(ServiceItem record);

    List<ServiceItem> selectByKey(ServiceItem record);

    int update(ServiceItem record);
}
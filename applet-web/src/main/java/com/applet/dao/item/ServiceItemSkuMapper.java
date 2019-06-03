package com.applet.dao.item;

import com.applet.model.item.ServiceItemSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ServiceItemSkuMapper {

    int insert(ServiceItemSku record);

    int update(ServiceItemSku record);

    List<ServiceItemSku> selectByKey(ServiceItemSku record);
}
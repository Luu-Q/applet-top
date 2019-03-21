package com.applet.dao.order;

import com.applet.model.order.SmallOrder;

public interface SmallOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SmallOrder record);

    int insertSelective(SmallOrder record);

    SmallOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SmallOrder record);

    int updateByPrimaryKey(SmallOrder record);
}
package com.applet.sms.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mapstruct.Mapper;

import java.util.Map;

@Mapper
public interface CityMapper {

    @Select("SELECT * FROM city WHERE code='1101' ")
    Map<String,Object> getCityNameByCode();

    @Update("update city set name = '北京市辖乐观',status=status+1 WHERE code='1101' and status=#{sta} ")
    int update(@Param("sta") int sta);

}

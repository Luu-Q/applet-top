package com.applet.dao.member;

import com.applet.model.member.CleanMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface CleanMemberMapper {

    int insert(CleanMember record);

    int update(CleanMember record);

    List<CleanMember> selectListByCondition(CleanMember cMember);

    @Select("SELECT * FROM clean_member WHERE clean_code = #{code}")
    CleanMember existedCleanCode(@Param("code") String code);

    @Select("SELECT * FROM clean_member WHERE id = #{id}")
    CleanMember selectById(@Param("id") Long id);

    int bindMobile(Map<String, Object> map);
}
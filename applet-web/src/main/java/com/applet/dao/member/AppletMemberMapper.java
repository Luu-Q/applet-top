package com.applet.dao.member;

import com.applet.model.member.AppletMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AppletMemberMapper {

    int insert(AppletMember record);

    int updateByOpendid(AppletMember record);

    @Select("SELECT * FROM applet_member a WHERE a.openid =#{openid}")
    AppletMember getMemberByOpenid(@Param("openid") String openid);
}
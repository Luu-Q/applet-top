package com.applet.model.member;

import com.applet.common.domain.BaseDomain;
import lombok.Data;

@Data
public class AppletMember extends BaseDomain {

    private String appid;

    private String openid;

    private String headUrl;

    private String mobile;

    private String nickname;

    private String realName;

    private String wechatNo;

    private String province;

    private String city;

    private String area;

    private String detailAddress;

    private Integer gender;

}
package com.applet.model.member;

import com.applet.common.domain.BaseDomain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CleanMember extends BaseDomain {

    private String loginName;

    /*生成json是不添加该属性*/
    @JsonIgnore
    private String loginPwd;
    @JsonIgnore
    private String salt;

    private String cleanCode;

    private String nickName;

    private String realName;

    private Integer gender;

    private Long birthday;

    private String memEmail;

    private String memPhoto;

    private String zipcode;

    private String nationality;

    private String province;

    private String city;

    private String county;

    private String addresss;

    private String company;

    private String alipay;

    private String mobile;

    private Integer mobileShow;

    private String wechat;

    private Integer wechatShow;

    private Integer qq;

    private Integer qqShow;

    private String idCard;

    private String hobby;

    private String signature;

    private Integer resourceOs;
    @JsonIgnore
    private Integer isDel;

    private Long lastLoginTime;
}
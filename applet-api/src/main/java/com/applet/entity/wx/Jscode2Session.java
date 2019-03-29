package com.applet.entity.wx;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Jscode2Session implements Serializable {

    @ApiModelProperty(value = "用户唯一标识")
    private String openid;

    @ApiModelProperty(value = "会话密钥")
    private String sessionKey;

    @ApiModelProperty(value = "sessionKey过期时间")
    private int expiresIn;

    @ApiModelProperty(value = "用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明")
    private String unionid;

    private String errcode;

    private String errmsg;
}

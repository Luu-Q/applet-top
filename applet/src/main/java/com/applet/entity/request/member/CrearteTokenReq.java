package com.applet.entity.request.member;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(value = "创建 token")
public class CrearteTokenReq implements Serializable {

    @ApiModelProperty(value = "用户id", required = true,example = "18")
    @NotNull(message = "用户id不能为空")
    private String memberId;

    @ApiModelProperty(value = "登录来源", required = true,example = "1")
    @NotNull(message = "登录来源不能为空")
    private Integer resourceOs;
}

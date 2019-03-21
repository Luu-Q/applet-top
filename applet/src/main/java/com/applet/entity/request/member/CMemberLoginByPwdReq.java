package com.applet.entity.request.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "账号密码登录入参")
public class CMemberLoginByPwdReq {

    @ApiModelProperty(value = "登录名", required = true,example = "15710011001")
    @NotNull(message = "账号不能为空")
    private String loginName;

    @ApiModelProperty(value = "密码", required = true,example = "154564")
    @NotNull(message = "密码不能为空")
    private String loginPwd;

    @ApiModelProperty(value = "注册来源" ,required = true,example = "1：PC，2：IOS，3：Android，4：微信平台，5：h5页面，6：微信小程序，99：不明来源")
    @NotNull(message = "登录来源不能为空")
    private Integer resourceOs;
}

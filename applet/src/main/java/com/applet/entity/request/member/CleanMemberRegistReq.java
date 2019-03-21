package com.applet.entity.request.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ToString
@ApiModel(value = "注册入参")
public class CleanMemberRegistReq {

    @ApiModelProperty(value = "登录名，只能为手机号", required = true,example = "15710011001")
    @NotNull(message = "账号不能为空")
    private String loginName;

    @ApiModelProperty(value = "密码", required = true,example = "154564")
    @NotNull(message = "密码不能为空")
    private String loginPwd;

    @ApiModelProperty(value = "验证码", required = true,example = "123456")
    @Pattern(regexp = "^\\d{6}$", message = "验证码长度不对")
    @NotNull(message = "验证码不能为空")
    private String verificationCode;

    @ApiModelProperty(value = "注册来源" ,required = true,example = "1：PC，2：IOS，3：Android，4：微信平台，5：h5页面，6：微信小程序，99：不明来源")
    @NotNull(message = "注册来源不能为空")
    private Integer resourceOs;

}

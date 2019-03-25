package com.applet.entity.request.member;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class CMemberLoginByCaptchaReq {

    @Pattern(regexp = "^\\d{11}$", message = "号码长度不正确")
    @NotNull(message = "手机号不能为空")
    private String loginName;

    @NotNull(message = "验证码不能为空")
    @Pattern(regexp = "^\\d{6}$", message = "验证码长度不对")
    private String verificationCode;

    @NotNull(message = "登录来源不能为空")
    private Integer resourceOs;
}

package com.applet.entity.request.member;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CMemberBingMobileReq {
    @NotNull(message = "验证码不能为空")
    private String verificationCode;// 验证码
    @NotNull(message = "手机号不能为空")
    private String mobile;          // 手机号
}

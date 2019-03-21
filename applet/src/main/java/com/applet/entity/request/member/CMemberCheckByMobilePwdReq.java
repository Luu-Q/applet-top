package com.applet.entity.request.member;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CMemberCheckByMobilePwdReq {
    @NotNull(message = "手机号不能为空")
    private String mobile;

    @NotNull(message = "密码不能为空")
    private String loginPwd;
}

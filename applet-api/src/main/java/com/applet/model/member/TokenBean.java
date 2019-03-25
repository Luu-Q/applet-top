package com.applet.model.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "登录 Token")
@AllArgsConstructor
@NoArgsConstructor
public class TokenBean {

    @ApiModelProperty(value = "获取到的凭证")
    private String token;
    @ApiModelProperty(value = "获取到的凭证")
    private String memberId;
    @ApiModelProperty(value = "用户id")
    private long createTime;
}

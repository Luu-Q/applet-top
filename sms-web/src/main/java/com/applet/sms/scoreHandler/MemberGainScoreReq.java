package com.applet.sms.scoreHandler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 会员获取积分入参实体
 * @author: LUNING
 * @create: 2019/4/4 11:09 AM
 */
@Data
@ApiModel(value = "积分")
public class MemberGainScoreReq {

    @ApiModelProperty(value = "userId",example = "1")
    private  Integer userId;

    @ApiModelProperty(value = "scoreCode",example = MemberScoreConstant.FIRST_LOGIN)
    private  String scoreCode;

    @ApiModelProperty(value = "orderNo",example = "123")
    private  String orderNo;

    @ApiModelProperty(value = "orderType",example = "2")
    private  Integer orderType;
}

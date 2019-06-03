package com.applet.sms.scoreHandler;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description: 会员获取积分入参实体
 * @author: LUNING
 * @create: 2019/4/4 11:09 AM
 */
@Data
public class MemberGainScoreReq {

    @NotNull(message = "用户ID不能为空")
    private Integer userId;

    /**
     * 被邀请人id
     **/
    private Integer inviteUserId;

    @NotNull(message = "积分code码不能为空")
    private Integer scoreRuleId;

    /**
     * 1：收入；2：支出
     **/
    @NotBlank(message = "积分类型不能为空")
    private Integer scoreType;

    /**
     * 观看视频时长，发布照片张数，发布视频时长
     **/
    private Integer quantity;

    /**
     * 订单号
     **/
    private String orderNo = "";

    /**
     * 订单类型
     **/
    private Integer orderType = 0;
}

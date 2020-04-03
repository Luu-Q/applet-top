package com.applet.scoreHandler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * @description: 积分规则类型
 * @author: LUNING
 * @create: 2019-04-16 10:20
 */
@AllArgsConstructor
public enum MemberScoreEnum {



    TASK_SCORE_FIRST_LOGIN(1, MemberScoreConstant.TASK_SCORE_FIRST_LOGIN),//每日首次登录
    TASK_SCORE_SIGN_IN(2, MemberScoreConstant.TASK_SCORE_SIGN_IN),//签到
    TASK_SCORE_COLLECT_PRODUCT(3, MemberScoreConstant.TASK_SCORE_FIRST_LOGIN),//收藏产品文章，路由到登录
    TASK_SCORE_WATCH_CHARM_VIDEO(4, MemberScoreConstant.TASK_SCORE_FIRST_LOGIN),//观看魅力中国城视频，路由到登录
    TASK_SCORE_WATCH_VIDEO(5, MemberScoreConstant.TASK_SCORE_FIRST_LOGIN),//观看视频，路由到每日登录
    TASK_SCORE_INVITE(6, MemberScoreConstant.TASK_SCORE_INVITE),
    TASK_SCORE_INVITED_PERSON(7, MemberScoreConstant.TASK_SCORE_INVITED_PERSON),
    TASK_SCORE_COMMENT_ON(8, MemberScoreConstant.TASK_SCORE_COMMENT_ON),
    TASK_SCORE_SINGLE_SHARE(9, MemberScoreConstant.TASK_SCORE_FIRST_LOGIN),//单次分享得积分，路由至每日登录
    TASK_SCORE_ANSWER_SCORE(11, MemberScoreConstant.TASK_SCORE_ANSWER_SCORE),
    //发布需要审核，逻辑相同
    TASK_SCORE_PUBLISH_TRAVEL_NOTES(10, MemberScoreConstant.TASK_SCORE_PUBLISH_TRAVEL_NOTES),//发布游记
    TASK_SCORE_RELEASE_PHOTOS(12, MemberScoreConstant.TASK_SCORE_PUBLISH_TRAVEL_NOTES),//发布照片
    TASK_SCORE_RELEASE_VIDEO(13, MemberScoreConstant.TASK_SCORE_PUBLISH_TRAVEL_NOTES),//发布段视频
    TASK_SCORE_PUBLISH_STRATEGY(14, MemberScoreConstant.TASK_SCORE_PUBLISH_TRAVEL_NOTES),//发布攻略

    ORDER_TICKET_SCORE(15, MemberScoreConstant.ORDER_TICKET_SCORE),
    ORDER_PLANES_SCORE(16, MemberScoreConstant.ORDER_PLANES_SCORE),
    ORDER_TRAIN_SCORE(17, MemberScoreConstant.ORDER_TRAIN_SCORE),
    ORDER_CRUISE_SCORE(18, MemberScoreConstant.ORDER_CRUISE_SCORE),
    ORDER_INSURANCE_SCORE(19, MemberScoreConstant.ORDER_INSURANCE_SCORE),
    ORDER_VISA_SCORE(20, MemberScoreConstant.ORDER_VISA_SCORE),
    ORDER_DESTINATION_SCORE(21, MemberScoreConstant.ORDER_DESTINATION_SCORE),
    ORDER_TOUR_SCORE(22, MemberScoreConstant.ORDER_TOUR_SCORE),
    ORDER_HOTEL_SCORE(23, MemberScoreConstant.ORDER_HOTEL_SCORE),
    ;


    private final int scoreId;
    private final String scoreCode;

    /**
     * @JsonCreator -> 反序列化
     */
    @JsonCreator
    public static MemberScoreEnum fromValue(int scoreId) {
        for (MemberScoreEnum retCode : MemberScoreEnum.values()) {
            if (retCode.getScoreId() == scoreId) {
                return retCode;
            }
        }
        throw new IllegalArgumentException("Invalid scoreRuleId : " + scoreId);
    }

    /**
     * @JsonValue -> 指定json序列化该值
     */
    @JsonValue
    public int getScoreId() {
        return scoreId;
    }

    public String getScoreCode() {
        return scoreCode;
    }
}

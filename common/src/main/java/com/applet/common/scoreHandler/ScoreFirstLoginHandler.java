package com.applet.common.scoreHandler;

import com.ykly.app.common.ResultModel;
import com.ykly.app.common.strategy.AbstractHandler;
import com.ykly.app.common.strategy.HandlerType;
import com.ykly.app.couponAndscore.service.MemberScoreDetailService;
import com.ykly.app.couponAndscore.vo.MemberGainScoreReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 每日首次登录得积分业务处理器 todo
 * @author: LUNING
 * @create: 2019/4/8 10:32 AM
 */
@Slf4j
@Component
@HandlerType(MemberScoreConstant.FIRST_LOGIN)
public class ScoreFirstLoginHandler extends AbstractHandler {

    @Autowired
    MemberScoreDetailService scoreDetailService;

    @Override
    public ResultModel handler(Class<?> clazz) {
        log.info("[积分]每日首次登录得积分 start...");
        try {
            MemberGainScoreReq req = (MemberGainScoreReq) clazz.newInstance();

        } catch (Exception e) {
            log.error("[积分]每日首次登录得积分 error...");
        }
        log.info("[积分]每日首次登录得积分 end...");
        return new ResultModel("每日首次登录得积分 逻辑处理");
    }
}

package com.applet.sms.scoreHandler;

import com.applet.common.result.ResultModel;
import com.applet.common.strategy.AbstractHandler;
import com.applet.common.strategy.BaseHandlerDto;
import com.applet.common.strategy.HandlerType;
import lombok.extern.slf4j.Slf4j;
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


    @Override
    public ResultModel handler(BaseHandlerDto baseHandlerDto) {
        log.info("[积分]每日首次登录得积分 start...");
        try {
            MemberGainScoreReq req = (MemberGainScoreReq) baseHandlerDto.getData();
            String scoreCode = req.getScoreCode();
            log.info("[积分]每日首次登录得积分 end..."+scoreCode);

        } catch (Exception e) {
            log.error("[积分]每日首次登录得积分 error...",e);
        }
        log.info("[积分]每日首次登录得积分 end...");

        return ResultModel.succWithData("每日首次登录得积分 逻辑处理");
    }
}
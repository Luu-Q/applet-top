package com.applet.common.scoreHandler;

import com.ykly.app.common.ResultModel;
import com.ykly.app.common.strategy.AbstractHandler;
import com.ykly.app.common.strategy.HandlerType;
import com.ykly.app.couponAndscore.service.MemberScoreDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 观看魅力中国城视频
 * @author: LUNING
 * @create: 2019/4/8 3:18 PM
 */
@Component
@HandlerType(MemberScoreConstant.WATCH_CHARM_VIDEO)
public class ScoreWatchCharmVideoHandler extends AbstractHandler {

    @Autowired
    MemberScoreDetailService scoreDetailService;

    @Override
    public ResultModel handler(Class<?> clazz) {
        //todo 观看魅力中国城视频 逻辑处理
        return new ResultModel("观看魅力中国城视频");
    }
}

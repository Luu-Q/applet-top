package com.applet.common.scoreHandler;

import com.ykly.app.common.ResultModel;
import com.ykly.app.common.strategy.AbstractHandler;
import com.ykly.app.common.strategy.HandlerType;
import com.ykly.app.couponAndscore.service.MemberScoreDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 收藏产品/文章得积分业务逻辑处理器
 * @author: LUNING
 * @create: 2019/4/8 10:34 AM
 */
@Component
@HandlerType(MemberScoreConstant.COLLECT_PRODUCT)
public class ScoreCollectProductHandler extends AbstractHandler {

    @Autowired
    MemberScoreDetailService scoreDetailService;

    @Override
    public ResultModel handler(Class<?> clazz) {
        //todo 收藏产品/文章得积分 逻辑处理
        return new ResultModel("收藏产品/文章得积分 逻辑处理");
    }

}

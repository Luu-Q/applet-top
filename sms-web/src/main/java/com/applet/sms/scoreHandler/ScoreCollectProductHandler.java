package com.applet.sms.scoreHandler;

import com.applet.common.result.ResultModel;
import com.applet.common.strategy.AbstractHandler;
import com.applet.common.strategy.BaseHandlerDto;
import com.applet.common.strategy.HandlerType;
import org.springframework.stereotype.Component;

/**
 * @description: 收藏产品/文章得积分业务逻辑处理器
 * @author: LUNING
 * @create: 2019/4/8 10:34 AM
 */
@Component
@HandlerType(MemberScoreConstant.TASK_SCORE_FIRST_LOGIN)
public class ScoreCollectProductHandler extends AbstractHandler {


    @Override
    public ResultModel handler(BaseHandlerDto baseHandlerDto) {
        //todo 收藏产品/文章得积分 逻辑处理
        return ResultModel.succWithData("收藏产品/文章得积分 逻辑处理");
    }

}

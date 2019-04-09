package com.applet.sms.controller;

import com.applet.common.result.ResultModel;
import com.applet.common.strategy.AbstractHandler;
import com.applet.common.strategy.BaseHandlerDto;
import com.applet.common.strategy.HandlerContext;
import com.applet.sms.scoreHandler.MemberGainScoreReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: C端会员积分明细
 * @author: LUNING
 * @create: 2019/4/4 11:01 AM
 */
@Api(value = "applet-address-api", description = "策略模式test")
@RestController
@RequestMapping("/member/score")
public class MemberScoreDetailController {


    @Autowired
    private HandlerContext handlerContext;


    @ApiOperation(value = "策略模式", notes = "策略模式", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    @PostMapping(value = "/addScore")
    public ResultModel addMemberScore(@RequestBody MemberGainScoreReq req, BindingResult result){
        if (result.hasErrors()) {
            return ResultModel.fail(result.getFieldError().getDefaultMessage());
        }

        AbstractHandler contextInstance = handlerContext.getInstance(req.getScoreCode());
        ResultModel handler = contextInstance.handler(new BaseHandlerDto<MemberGainScoreReq>(req));
        return handler;

    }
}

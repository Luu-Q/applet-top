package com.applet.controller.order;

import com.applet.common.redis.RedisTemplateUtils;
import com.applet.common.result.ResultModel;
import com.applet.config.rest.RestTemplateUtils;
import com.applet.entity.request.order.CreateOrderReq;
import com.applet.service.order.OrderService;
import com.applet.sms.client.SmsClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "applet-order-api", description = "订单相关")
@RequestMapping("/order")
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    SmsClient smsClient;
    @Autowired
    RestTemplateUtils restTemplateUtils;
    @Autowired
    RedisTemplateUtils redisTemplate;


    @ApiOperation(value = "下单", notes = "下单", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    @PostMapping("/createOrder")
    public ResultModel<?> createOrder(@RequestBody @Valid CreateOrderReq req, BindingResult result) {
        if (result.hasErrors()) {
            return ResultModel.fail(result.getFieldError().getDefaultMessage());
        }


        return orderService.createOrder(req);
    }

    @GetMapping("/test")
    public ResultModel<?> test() {
        String test = smsClient.test("路宁");

        return ResultModel.succWithData(test);
    }


}

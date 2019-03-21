package com.applet.controller.mq;

import com.applet.config.rabbitMQ.config.RabbitConfig;
import com.applet.entity.request.member.CrearteTokenReq;
import com.applet.entity.result.ResultModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mq1")
public class MqController {


    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/send1")
    public ResultModel<?> jscode2session() {


        CrearteTokenReq tokenReq = new CrearteTokenReq();
        tokenReq.setMemberId("路宁");
        tokenReq.setResourceOs(10);

        rabbitTemplate.convertAndSend(RabbitConfig.REGISTER_EXCHANGE_NAME,RabbitConfig.ROUTING_KEY,tokenReq);

        return ResultModel.succWithData("success");
    }
}

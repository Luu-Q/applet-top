package com.applet.sms.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mq2")
public class RabbitMqController {


    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/send2")
    public Object mqtest(@RequestParam("msg") String msg){

        rabbitTemplate.convertAndSend("okong",msg);

        return "消息：" + msg + ",已发送";
    }

}

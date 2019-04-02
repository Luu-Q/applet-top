package com.applet.common.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RabbitTemplateUtils {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public boolean convertAndSend(String exchange,String routingKey,Object object) throws Exception{
        MessagePostProcessor messagePostProcessor = message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            //设置编码
            messageProperties.setContentEncoding("utf-8");
            //设置过期时间10*1000毫秒
            messageProperties.setExpiration("10000");
            messageProperties.setContentType(MessageProperties.DEFAULT_CONTENT_TYPE);
            messageProperties.setDeliveryMode(MessageProperties.DEFAULT_DELIVERY_MODE);//持久化设置
            return message;
        };

        Message message = null;

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(exchange,routingKey,message,messagePostProcessor,correlationData);
        return true;
    }


}

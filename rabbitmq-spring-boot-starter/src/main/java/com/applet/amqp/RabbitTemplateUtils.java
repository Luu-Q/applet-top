package com.applet.amqp;

import com.applet.amqp.messages.RabbitmqMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RabbitTemplateUtils {

    /**
     * 描述 : 应用名称
     */
    @Value("${spring.application.name}")
    private String springApplicationName;

    @Autowired
    RabbitTemplate rabbitTemplate;

    public <T> Object convertAndSend(RabbitmqMessage<T> rabbitmqMessage) throws Exception{
        if(null == rabbitmqMessage){
            return null;
        }
        if(StringUtils.isEmpty(rabbitmqMessage.getExchange())){
            return null;
        }
        if(null == rabbitmqMessage.getBody()){
            return null;
        }

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

        rabbitmqMessage.setSender(springApplicationName);
        rabbitmqMessage.setSendDate(new Date());
        rabbitmqMessage.setCreateDate(new Date());
        rabbitmqMessage.setTimestamp(System.currentTimeMillis());

        CorrelationData correlationData = new CorrelationData(rabbitmqMessage.getId());

        rabbitTemplate.convertAndSend(rabbitmqMessage.getExchange(),rabbitmqMessage.getRoutingKey(),rabbitmqMessage.getBody(),messagePostProcessor,correlationData);
        return true;
    }


}

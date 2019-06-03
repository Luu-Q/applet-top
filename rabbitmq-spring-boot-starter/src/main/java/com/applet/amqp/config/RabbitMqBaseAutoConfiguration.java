package com.applet.amqp.config;

import com.applet.amqp.RabbitTemplateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: todo
 * @author: LUNING
 * @create: 2019-04-17 14:27
 */
@Configuration
@ConditionalOnClass({RabbitTemplate.class,RabbitTemplateUtils.class})
@ConditionalOnMissingBean(ConnectionFactory.class)
@EnableConfigurationProperties(RabbitMqBaseProperties.class)
public class RabbitMqBaseAutoConfiguration {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        connectionFactory.setPublisherConfirms(true);//支持发布确定
        connectionFactory.setPublisherReturns(true);//支持发布返回
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            // 消息回调确认失败处理
            if (!ack) {// && (correlationData instanceof CorrelationData || null == correlationData)

                log.info("MQ消息发送失败:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
            } else {
                log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
            }
        });
        /**
         * 用于实现消息发送到RabbitMQ交换器，但无相应队列与交换器绑定时的回调。
         * 基本上来说线上不可能出现这种情况，除非手动将已经存在的队列删掉，否则在测试阶段肯定能测试出来。
         */
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("消息丢失: message({}),replyCode({}),replytext({}),exchange({}),routingKey({})", message, replyCode, replyText, exchange, routingKey);
            // TODO 保存消息到数据库
        });
        return rabbitTemplate;
    }
}

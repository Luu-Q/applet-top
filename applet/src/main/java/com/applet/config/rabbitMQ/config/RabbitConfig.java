package com.applet.config.rabbitMQ.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitConfig {


    public static final String REGISTER_QUEUE_NAME = "test.topic.queue";
    public static final String REGISTER_EXCHANGE_NAME = "test.topic.exchange";
    public static final String ROUTING_KEY = "test.topic";


    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause));
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> log.info("消息丢失: message({}),replyCode({}),replytext({}),exchange({}),routingKey({})", message, replyCode, replyText, exchange, routingKey));
        return rabbitTemplate;
    }

    /**
     * 设置消息转换.
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }


    @Bean
    public Queue testQueue() {
        /**
         * 构造一个新队列，给定名称、持久性标志、自动删除标志和参数
         * @param name 队列的名称—必须不为空;设置为“”以让代理生成名称。
         * @param durable true如果我们声明一个持久队列(队列将在服务器重启后继续存在)
         * @param exclusive true如果我们声明一个独占队列(该队列将只被庄家使用连接)
         * @param autoDelete true，如果服务器应该在队列不再使用时删除它
         * @param arguments 参数用于声明队列的参数
         */
        //默认值
        return new Queue(REGISTER_QUEUE_NAME, true,false,false,null);
    }


    @Bean
    public TopicExchange testTopicExchange() {
        /**
         * 构造一个新的Exchange，给定一个名称、持久性标志和自动删除标志，以及参数
         * @param name 交换器的名称
         * @param durable true如果我们声明一个持久的交换(该交换将在服务器重启后继续存在)
         * @param autoDelete true自动删除为真，如果服务器应该删除交换时，它是no不再使用的
         * @param arguments 参数用于声明交换的参数
         */
        //默认值
        return new TopicExchange(REGISTER_EXCHANGE_NAME,true,false,null);
    }

    /**
     * 队列绑定到交换机
     */
    @Bean
    public Binding testBinding() {
        return BindingBuilder.bind(testQueue()).to(testTopicExchange()).with(ROUTING_KEY);
    }


}

package com.applet.mqhandler;

import com.applet.common.rabbitmq.RabbitMQConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * 死信队列
 */
@Component
public class DeadLetterQueues {


    /**
     * 死信队列跟交换机类型没有关系 不一定为directExchange  不影响该类型交换机的特性.
     *
     * @return the exchange
     */
    @Bean("deadLetterExchange")
    public Exchange deadLetterExchange() {
        return ExchangeBuilder.directExchange(RabbitMQConstant.TEST_DEAD_LETTER_EXCHANGE_NAME).durable(true).build();
    }

    /**
     * 声明一个死信队列.
     * x-dead-letter-exchange   对应  死信交换机
     * x-dead-letter-routing-key  对应 死信队列
     *
     * @return the queue
     */
    @Bean("deadLetterQueue")
    public Queue deadLetterQueue() {
        Map<String, Object> args = new HashMap<>(2);
        //x-dead-letter-exchange    声明  死信交换机
        args.put(RabbitMQConstant.DEAD_LETTER_EXCHANGE_KEY, RabbitMQConstant.TEST_DEAD_LETTER_EXCHANGE_NAME);
        //x-dead-letter-routing-key    声明 死信路由键
        args.put(RabbitMQConstant.DEAD_LETTER_ROUTING_KEY, RabbitMQConstant.TEST_TOPIC_ROUTING_DL_KEY_R);
        return QueueBuilder.durable(RabbitMQConstant.TEST_DEAD_LETTER_QUEUE).withArguments(args).build();
    }

    /**
     * 定义死信队列转发队列.普通队列
     *
     * @return the queue
     */
    @Bean("redirectQueue")
    public Queue redirectQueue() {
        return QueueBuilder.durable(RabbitMQConstant.TEST_DEAD_LETTER_REDIRECT_QUEUE).build();
    }

    /**
     * 死信路由通过 DL_KEY 绑定键绑定到死信队列上.
     *
     * @return the binding
     */
    @Bean
    public Binding deadLetterBinding() {
        return new Binding(RabbitMQConstant.TEST_DEAD_LETTER_QUEUE, Binding.DestinationType.QUEUE, RabbitMQConstant.TEST_DEAD_LETTER_EXCHANGE_NAME, RabbitMQConstant.TEST_TOPIC_ROUTING_DL_KEY, null);
    }

    /**
     * 死信路由通过 KEY_R 绑定键绑定到死信队列上.普通队列绑定到交换机
     *
     * @return the binding
     */
    @Bean
    public Binding redirectBinding() {
        return new Binding(RabbitMQConstant.TEST_DEAD_LETTER_REDIRECT_QUEUE, Binding.DestinationType.QUEUE, RabbitMQConstant.TEST_DEAD_LETTER_EXCHANGE_NAME, RabbitMQConstant.TEST_TOPIC_ROUTING_DL_KEY_R, null);
    }
}

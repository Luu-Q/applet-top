package com.applet.common.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        connectionFactory.setPublisherConfirms(true);//支持发布确定
        connectionFactory.setPublisherReturns(true);//支持发布返回
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setMandatory(true);
        /**
         *
         * RabbitMQ提供transaction和confirm模式来确保生产者不丢消息。
         * transaction机制：发送消息前，开启事物(channel.txSelect())，然后发送消息，如果发送过程中出现什么异常，事物就会回滚(channel.txRollback())，如果发送成功则提交事物(channel.txCommit())。
         *            缺点：就是吞吐量下降了。生产上用confirm模式的居多。一旦channel进入confirm模式，所有在该信道上面发布的消息都将会被指派一个唯一的ID(从1开始)，一旦
         *                 消息被投递到所有匹配的队列之后，rabbitMQ就会发送一个Ack给生产者(包含消息的唯一ID)，这就使得生产者知道消息已经正确到达目的队列了.如果rabiitMQ没能处理该消息，则会发送一个Nack消息给你，你可以进行重试操作。
         * confirm模式
         * 用于实现消息发送到RabbitMQ交换器后接收ack回调。
         * 如果消息发送确认失败就进行重试。重试还失败就消息入库
         * 此处只能确认消息发送失败
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            // 消息回调确认失败处理
            if (!ack) {// && (correlationData instanceof CorrelationData || null == correlationData)

                log.info("MQ消息发送失败:correlationData({}),ack({}),cause({})", correlationData, ack, cause);

                /*//消息发送失败,就进行重试，重试过后还不能成功就记录到数据库
//                if (correlationData.getRetryCount() < systemConfig.getMqRetryCount()) {
                if (true) {
                    log.info("MQ消息发送失败，消息重发，消息ID：{}", correlationData);

                    // 将重试次数加一
//                    correlationDataExtends.setRetryCount(correlationDataExtends.getRetryCount() + 1);

                    // 重发发消息
//                    this.convertAndSend(correlationDataExtends.getExchange(), correlationDataExtends.getRoutingKey(),
//                            correlationDataExtends.getMessage(), correlationDataExtends);
                } else {
                    //消息重试发送失败,将消息放到数据库等待补发
                    log.warn("MQ消息重发失败，消息入库，消息ID：{}", correlationData);

                    // TODO 保存消息到数据库
                }*/
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

    /**
     * 设置消息转换.
     *
     * @return
     */
    /*@Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }*/


}

/**
 * 消息的丢失问题
 * 在数据库创建一张异常消息表。
 * <p>
 * 在发消息的时候如果出现异常，直接将消息记录到异常消息表，等待后台跑批，进行补偿发放。
 * 在发消息的时候，如果发送消息的ack回调没没有发送成功，将进行消息重发，如果重发3次还是失败，该消息就记录到异常消息表，等待后台跑批，进行补偿发放。消息的重复发送可以使用RabbitMQ的ConfirmCallback、ReturnCallback机制来实现。
 * 在消费端处理消息（调用奖品系统发放奖励）的时候，如果出现异常也将消息放到异常消息表中，等待后台跑批，进行补偿发放。如果将异常消息保存到数据库时发生了异常，则将消息放到死信队列，等待后台跑批，进行补偿发放。
 * 这样子虽然还是不能完全杜绝消息丢失，但是绝大部分情况下是没有问题的。
 * <p>
 * 重复消费问题
 * 为每个消息生成业务流水号，将流水号和发放里的参数一起发送到奖品系统，奖品系统在发放奖励的时候先判断这个流水号是否存在，存在就表示该奖品已经发过来直接返回发放成功，如果没有就进行发放奖励操作。
 */

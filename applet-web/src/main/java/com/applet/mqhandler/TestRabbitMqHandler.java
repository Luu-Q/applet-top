package com.applet.mqhandler;

import com.applet.common.rabbitmq.RabbitMQConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class TestRabbitMqHandler {

//    @RabbitListener(queues = {RabbitMQConstant.REGISTER_QUEUE_NAME})
    public void deleteCollectionQueueListener(Integer collectionId, Message message, Channel channel) {
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("[deleteCollectionQueueListener 监听的消息] - [collectionId]", collectionId);
//            int deleteNum = ykProductCollectionService.deleteCollectionByPrimaryKey(collectionId);
            // TODO 通知 MQ 消息已被成功消费,可以ACK了
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            try {
                // TODO 处理失败,重新压入MQ
                channel.basicRecover();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /*    @RabbitListener(queues = {RabbitConfig.DELETE_INVOICE_QUEUE})
    public void deleteInvoiceQueueListener(Integer invoiceId , Message message,Channel channel){
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("[deleteInvoiceQueueListener 监听的消息] - [invoiceId]", invoiceId);
            int deleteNum = ykAppInvoiceService.deleteInvoiceByPrimaryKey(invoiceId);
            // TODO 通知 MQ 消息已被成功消费,可以ACK了
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            try {
                // TODO 处理失败,重新压入MQ
                channel.basicRecover();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }*/

    /*@RabbitListener(queues = {RabbitConfig.REGISTER_QUEUE_NAME})
    public void listenerDelayQueue(Integer invoiceId, Message message, Channel channel) {
        log.info("[listenerDelayQueue 监听的消息] - [消费时间] - [{}] - [{}]", LocalDateTime.now(), invoiceId.toString());
        try {
            // TODO 通知 MQ 消息已被成功消费,可以ACK了
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            // TODO 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列
        }
    }*/
}

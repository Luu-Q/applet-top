package com.applet.mqhandler;

import com.alibaba.fastjson.JSONObject;
import com.applet.amqp.constant.RabbitMQConstant;
import com.applet.entity.wx.Jscode2Session;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class TestRabbitMqHandler {


    /* ========================== 简单队列 hello world ========================== */

//    @RabbitListener(queues = {RabbitMQConstant.TEST_HELLOWORLD})
    public void hello2(Integer collectionId, Message message, Channel channel) {
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("[hello2 监听的消息] - [collectionId={}]", collectionId);
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            try {
                channel.basicRecover();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /* ========================== 工作模式 work ========================== */

//    @RabbitListener(queues = {RabbitMQConstant.TEST_WORK})
    public void work1(Integer collectionId, Message message, Channel channel) {
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            /**
             * 默认是轮询
             * @param prefetchCount：告诉MQ不要同时给一个消费者推送超过prefetchCount个消息,即一点prefetchCount个消息没有应答，该消费者就会发生阻塞
             * @param global：指的是该设置是针对该consumer还是针对channel级别
             */
            channel.basicQos(1, false);
            log.info("[work1 监听的消息] - [collectionId={}]", collectionId);
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            try {
                channel.basicRecover();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

//    @RabbitListener(queues = {RabbitMQConstant.TEST_WORK})
    public void work2(Integer collectionId, Message message, Channel channel) {

        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            channel.basicQos(3, false);
            log.info("[work2 监听的消息] - [collectionId={}]", collectionId);
            channel.basicAck(deliveryTag, true);
        } catch (IOException e) {
            try {
                channel.basicRecover();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /* ========================== 发布订阅 Publish/Subscribe ========================== */
/*
    @RabbitListener(queues = {RabbitMQConstant.TEST_FANOUT_A})
    public void fanoutA(Integer collectionId, Message message, Channel channel) {
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("[fanoutA 监听的消息] - [collectionId={}]", collectionId);
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            try {
                channel.basicRecover();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @RabbitListener(queues = {RabbitMQConstant.TEST_FANOUT_B})
    public void fanoutB(Integer collectionId, Message message, Channel channel) {

        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("[fanoutB 监听的消息] - [collectionId={}]", collectionId);
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            try {
                channel.basicRecover();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @RabbitListener(queues = {RabbitMQConstant.TEST_FANOUT_C})
    public void fanoutC(@Header(name = "amqp_deliveryTag") long deliveryTag,
                        @Header("amqp_redelivered") boolean redelivered,Integer collectionId, Message message, Channel channel) {

        final long deliveryTag1 = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("[fanoutC 监听的消息] - [collectionId={}]", collectionId);
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            try {
                channel.basicRecover();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }*/

    /* ========================== 路由。主题模式  ========================== */

    /*@RabbitListener(queues = {RabbitMQConstant.TEST_TOPIC_QUEUE_NAME_A})
    public void topicQueueA(Message message, Channel channel) {
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("[{}]消息队列接收数据，消息：{}", RabbitMQConstant.TEST_TOPIC_QUEUE_NAME_A,message);
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            try {
                channel.basicRecover();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }*/

    AtomicInteger count = new AtomicInteger(0);

    @RabbitListener(queues = {RabbitMQConstant.TEST_TOPIC_QUEUE_NAME_B})
    public void process(Message message, Channel channel) throws IOException {

        count.getAndIncrement();

        MessageProperties messageProperties = message.getMessageProperties();

        log.info("[{}]消息队列接收数据，消息：{}", RabbitMQConstant.TEST_TOPIC_QUEUE_NAME_B,message);

        try {
            // TODO 处理消息
            int i = 1 / 0;
            // 确认消息已经消费成功
            channel.basicAck(messageProperties.getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("[{}]MQ消息处理异常，消息:{}", RabbitMQConstant.TEST_TOPIC_QUEUE_NAME_B,message, e);
            try {
                // TODO 保存消息到数据库
                int i = 1 / 0;
                // 确认消息已经消费成功
                channel.basicAck(messageProperties.getDeliveryTag(), false);
            } catch (Exception e1) {
                log.error("[{}]保存异常MQ消息到数据库异常，放到死信队列，消息：{}",RabbitMQConstant.TEST_TOPIC_QUEUE_NAME_B, message,e1);
                // 将消息重新压入队列头
                channel.basicNack(messageProperties.getDeliveryTag(), false, true);
            }
        }

        System.out.println("【"+RabbitMQConstant.TEST_TOPIC_QUEUE_NAME_B+"】"+count);
    }

    /* ======================  死信队列 start =========================== */

//    @RabbitListener(queues = {RabbitMQConstant.TEST_DEAD_LETTER_QUEUE})
    public void deadLetterQueue(Message message, Channel channel) throws IOException {

        MessageProperties messageProperties = message.getMessageProperties();

        log.info("[{}]消息队列接收数据，消息：{}", RabbitMQConstant.TEST_DEAD_LETTER_QUEUE,message);

        try {
            // TODO 处理消息
            int i = 1 / 0;
            // 确认消息已经消费成功
            channel.basicAck(messageProperties.getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("[{}]MQ消息处理异常，消息:{}", RabbitMQConstant.TEST_DEAD_LETTER_QUEUE,message, e);
            try {
                // TODO 保存消息到数据库
                int i = 1 / 0;
                // 确认消息已经消费成功
                channel.basicAck(messageProperties.getDeliveryTag(), false);
            } catch (Exception e1) {
                log.error("[{}]保存异常MQ消息到数据库异常，放到死信队列，消息：{}",RabbitMQConstant.TEST_DEAD_LETTER_QUEUE, message);
                // 确认消息将消息放到死信队列
                channel.basicNack(messageProperties.getDeliveryTag(), false, false);
            }
        }
    }

    @RabbitListener(queues = {RabbitMQConstant.TEST_DEAD_LETTER_REDIRECT_QUEUE})
    public void deadLetterQueueRedirect(Message message, Channel channel) throws IOException {

        MessageProperties messageProperties = message.getMessageProperties();

        log.info("[{}]消息队列接收数据，消息：{}", RabbitMQConstant.TEST_DEAD_LETTER_REDIRECT_QUEUE,message);

        try {
            // TODO 处理消息
            Jscode2Session jscode2Session = JSONObject.parseObject(new String(message.getBody()), Jscode2Session.class);
            /**
             * 确认消息已经消费成功
             * @param deliveryTag 该消息的index
             * @param multiple 是否批量.true:将一次性ack所有小于deliveryTag的消息 false 只ack当前消息。
             */
            channel.basicAck(messageProperties.getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("[{}]MQ消息处理异常，消息:{}", RabbitMQConstant.TEST_DEAD_LETTER_REDIRECT_QUEUE,message, e);
            try {
                // TODO 保存消息到数据库
                // 确认消息已经消费成功
                channel.basicAck(messageProperties.getDeliveryTag(), false);
            } catch (Exception e1) {
                log.error("[{}]保存异常MQ消息到数据库异常，放到死信队列，消息：{}",RabbitMQConstant.TEST_DEAD_LETTER_REDIRECT_QUEUE, message);
                // 确认消息将消息放到死信队列
                /**
                 * @param deliveryTag:该消息的index
                 * @param multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
                 * @param requeue：被拒绝的是否重新入队列,false不重新入队，true重新入队。
                 */
                channel.basicNack(messageProperties.getDeliveryTag(), false, false);
                //channel.basicNack 与 channel.basicReject 的区别在于basicNack可以拒绝多条消息，而basicReject一次只能拒绝一条消息
//                channel.basicReject(messageProperties.getDeliveryTag(), false);
            }
        }
    }

    /* ======================  死信队列 end =========================== */

}

package com.applet.sms.config.mq;// package com.jiuyi.credit.cash.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 监听 test.topic 队列
 */
@Component
@Slf4j
public class TestTopicReceiver {



    @RabbitListener(queues = "acct.cardLoan.settle", containerFactory = "rabbitListenerContainerFactory")
    @RabbitHandler
    public void receiveRepayQueue(Message message) {
        try {
            String msg = new String(message.getBody());

        } catch (Exception e) {
            log.error("[TEST-TOPIC消息]处理过程出错：", e);
        }
    }
}

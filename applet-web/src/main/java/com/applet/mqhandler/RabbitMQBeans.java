package com.applet.mqhandler;

import com.applet.common.rabbitmq.RabbitMQConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQBeans {

    /* ============================== 路由模式、主题模式================================== */
    /**
     * Routing路由模式:需要将一个队列绑定到交换机上，要求该消息与一个特定的路由键完全匹配，这是一个完整的匹配。
     * Topics主题模式:发送端不只按固定的routing key发送消息，而是按字符串匹配发送，接收端同样如此
     *                符号#匹配一个或多个词，符号*匹配不多不少一个词。
     */

    @Bean
    public TopicExchange topicExchange() {
        /**
         * 构造一个新的Exchange，给定一个名称、持久性标志和自动删除标志，以及参数
         * @param name 交换器的名称
         * @param durable true如果我们声明一个持久的交换(该交换将在服务器重启后继续存在)
         * @param autoDelete 当所有与之绑定的消息队列都完成了对此交换机的使用后，删掉它
         * @param arguments 参数用于声明交换的参数
         */
        //默认值
        return new TopicExchange(RabbitMQConstant.TEST_TOPIC_EXCHANGE_NAME,true,false,null);
    }
    /** 路由模式，使用direct交换机 TEST_TOPIC_ROUTING_KEY_A 全部匹配才能被消费 */
    @Bean
    public Binding topicBindingA(Queue topicQueueA,TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueueA).to(topicExchange).with(RabbitMQConstant.TEST_TOPIC_ROUTING_KEY_A);
    }
    /** Topics主题模式， TEST_TOPIC_ROUTING_KEY 前缀匹配就能被消费 */
    @Bean
    public Binding topicBindingB(Queue topicQueueB,TopicExchange topicExchange) {
        //队列绑定到交换机
        return BindingBuilder.bind(topicQueueB).to(topicExchange).with(RabbitMQConstant.TEST_TOPIC_ROUTING_KEY);
    }

    @Bean
    public Queue topicQueueA() {
        /**
         * 构造一个新队列，给定名称、持久性标志、自动删除标志和参数
         * @param name 队列的名称—必须不为空;设置为“”以让代理生成名称。
         * @param durable true如果我们声明一个持久队列(队列将在服务器重启后继续存在)
         * @param exclusive true如果我们声明一个独占队列(该队列将只被庄家使用连接)
         *                  排他队列，如果一个队列被声明为排他队列，该队列仅对首次申明它的连接可见，并在连接断开时自动删除。这里需要注意三点：
         * 　　               1. 排他队列是基于连接可见的，同一连接的不同信道是可以同时访问同一连接创建的排他队列；
         * 　　               2.“首次”，如果一个连接已经声明了一个排他队列，其他连接是不允许建立同名的排他队列的，这个与普通队列不同；
         * 　　               3.即使该队列是持久化的，一旦连接关闭或者客户端退出，该排他队列都会被自动删除的，这种队列适用于一个客户端发送读取消息的应用场景。
         * @param autoDelete true，自动删除，如果该队列没有任何订阅的消费者的话，该队列会被自动删除。这种队列适用于临时队列。
         * @param arguments 参数用于声明队列的参数
         */
        //默认值
        return new Queue(RabbitMQConstant.TEST_TOPIC_QUEUE_NAME_A, true,false,false,null);
    }
    @Bean
    public Queue topicQueueB() {
        return new Queue(RabbitMQConstant.TEST_TOPIC_QUEUE_NAME_B);
    }


    /* ========================== 发布订阅 Publish/Subscribe，广播模式 ========================== */
    /**
     * fanoutExchange交换机,即使指定了 routingKey 也不起作用
     * 广播模式 - 扇出
     * 只要绑定到交换机的队列都能消费，一条消息多个消费
     * 工作模式是同一个消息只能有一个消费者
     */

//    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitMQConstant.TEST_FANOUT_EXCHANGE);
    }
//    @Bean
    public Queue AMessage() {
        return new Queue(RabbitMQConstant.TEST_FANOUT_A);
    }

//    @Bean
    public Queue BMessage() {
        return new Queue(RabbitMQConstant.TEST_FANOUT_B);
    }

//    @Bean
    public Queue CMessage() {
        return new Queue(RabbitMQConstant.TEST_FANOUT_C);
    }
//    @Bean
    Binding bindingExchangeA(Queue AMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(AMessage).to(fanoutExchange);
    }

//    @Bean
    Binding bindingExchangeB(Queue BMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(BMessage).to(fanoutExchange);
    }

//    @Bean
    Binding bindingExchangeC(Queue CMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(CMessage).to(fanoutExchange);
    }

    /* ========================== 工作模式 work ========================== */
    /** 工作模式、简单模式性质一样，工作模式为多分消费者
     *  简单模式：隐患 消息可能没有被消费者正确处理,已经从队列中消失了,造成消息的丢失
     *  工作模式：隐患,高并发情况下,默认会产生某一个消息被多个消费者共同使用
     */
//    @Bean
    public Queue workQueue() {
        return new Queue(RabbitMQConstant.TEST_WORK);
    }


    /* ========================== 简单队列 hello world ========================== */
//    @Bean
    public Queue helloWorldQueue() {
        return new Queue(RabbitMQConstant.TEST_HELLOWORLD);
    }
}

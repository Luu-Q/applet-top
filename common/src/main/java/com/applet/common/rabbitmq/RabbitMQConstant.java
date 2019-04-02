package com.applet.common.rabbitmq;

public class RabbitMQConstant {

    public static final String TEST_HELLOWORLD = "test.helloworld";

    public static final String TEST_WORK = "test.work";

    public static final String TEST_FANOUT_A = "test.fanout.A";
    public static final String TEST_FANOUT_B = "test.fanout.B";
    public static final String TEST_FANOUT_C = "test.fanout.C";
    public static final String TEST_FANOUT_EXCHANGE = "test.fanout.exchange";

    public static final String TEST_TOPIC_QUEUE_NAME_A = "test.topic.queueA";
    public static final String TEST_TOPIC_QUEUE_NAME_B = "test.topic.queueB";
    public static final String TEST_TOPIC_QUEUE_NAME_C = "test.topic.queueC";
    public static final String TEST_TOPIC_EXCHANGE_NAME = "test.topic.exchange";
    public static final String TEST_TOPIC_ROUTING_KEY_A = "test.topic.keyA";
    public static final String TEST_TOPIC_ROUTING_KEY = "test.topic.#";

    /* ======================  死信队列 start =========================== */
    /**
     * 死信队列 交换机标识符
     */
    public static final String DEAD_LETTER_EXCHANGE_KEY = "x-dead-letter-exchange";
    /**
     * 死信队列交换机绑定键标识符
     */
    public static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    public static final String TEST_DEAD_LETTER_EXCHANGE_NAME = "test.dl.exchange";
    public static final String TEST_DEAD_LETTER_QUEUE = "test.dl.queue";
    public static final String TEST_DEAD_LETTER_REDIRECT_QUEUE = "test.dl.queue.redirect";
    public static final String TEST_TOPIC_ROUTING_DL_KEY = "test.dl_key";
    public static final String TEST_TOPIC_ROUTING_DL_KEY_R = "test.dl_key_r";

    /* ======================  死信队列 end =========================== */

}

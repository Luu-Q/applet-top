package com.applet.controller.mq;

import com.applet.amqp.RabbitTemplateUtils;
import com.applet.amqp.constant.RabbitMQConstant;
import com.applet.amqp.messages.RabbitmqMessage;
import com.applet.common.result.ResultModel;
import com.applet.common.utils.date.DateUtil;
import com.applet.entity.wx.Jscode2Session;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Api(value = "rabbitmq", description = "rabbitmq测试接口")
@RestController
@RequestMapping("/rabbitmq")
@Slf4j
public class MqController {


    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    RabbitTemplateUtils rabbitTemplateUtils;

    @ApiOperation(value = "简单模式", notes = "简单模式", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    @GetMapping("/hello")
    public ResultModel<?> hello() {

        String currentDateTime = DateUtil.getCurrentDateTime();
        rabbitTemplate.convertAndSend(RabbitMQConstant.TEST_HELLOWORLD, currentDateTime);

        return ResultModel.succWithData(currentDateTime);
    }

    @ApiOperation(value = "工作模式", notes = "工作模式", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    @GetMapping("/work")
    public ResultModel<?> work() {

        String currentDateTime = DateUtil.getCurrentDateTime();
        rabbitTemplate.convertAndSend(RabbitMQConstant.TEST_WORK,currentDateTime);

        return ResultModel.succWithData(currentDateTime);
    }

    @ApiOperation(value = "发布订阅 Publish/Subscribe[fanout]", notes = "发布订阅 Publish/Subscribe[fanout]", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    @GetMapping("/fanoutExchanges")
    public ResultModel<?> fanoutExchanges() {

        String currentDateTime = DateUtil.getCurrentDateTime();
        rabbitTemplate.convertAndSend(RabbitMQConstant.TEST_FANOUT_EXCHANGE, StringUtils.EMPTY,currentDateTime);

        return ResultModel.succWithData(currentDateTime);
    }

    @ApiOperation(value = "topic路由模式/通配符（主题）", notes = "topic路由模式/通配符（主题）", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    @GetMapping("/topicExchange")
    public ResultModel<?> topicExchange() throws Exception {

        Jscode2Session jscode2Session = new Jscode2Session();
        jscode2Session.setErrcode("111");
        jscode2Session.setErrmsg("消息");
        jscode2Session.setExpiresIn(11);
        jscode2Session.setSessionKey("key");
        jscode2Session.setUnionid(UUID.randomUUID().toString());

        RabbitmqMessage message = new RabbitmqMessage();
        message.setId(UUID.randomUUID().toString());
        message.setExchange(RabbitMQConstant.TEST_TOPIC_EXCHANGE_NAME);
        message.setRoutingKey(RabbitMQConstant.TEST_TOPIC_ROUTING_KEY_A);
        message.setBody(jscode2Session);


        //路由
        rabbitTemplateUtils.convertAndSend(message);
//        rabbitTemplate.convertAndSend(RabbitMQConstant.TEST_TOPIC_EXCHANGE_NAME,RabbitMQConstant.TEST_TOPIC_ROUTING_KEY_A,jscode2Session,correlationData);
        Thread.sleep(20);
//        //主题
//        rabbitTemplate.convertAndSend(RabbitMQConstant.TEST_TOPIC_EXCHANGE_NAME,RabbitMQConstant.TEST_TOPIC_ROUTING_KEY,currentDateTime,correlationData);
//        //随便定义的前缀的key
//        rabbitTemplate.convertAndSend(RabbitMQConstant.TEST_TOPIC_EXCHANGE_NAME,RabbitMQConstant.TEST_TOPIC_ROUTING_KEY+"me",currentDateTime,correlationData);

        return ResultModel.succWithData("success");
    }

    @ApiOperation(value = "死信队列", notes = "死信队列", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    @GetMapping("/deadLetterQueue")
    public ResultModel<?> deadLetterQueue()  {

        Jscode2Session jscode2Session = new Jscode2Session();
        jscode2Session.setErrcode("111");
        jscode2Session.setErrmsg("消息");
        jscode2Session.setExpiresIn(11);
        jscode2Session.setSessionKey("key");
        jscode2Session.setUnionid(UUID.randomUUID().toString());

        RabbitmqMessage message = new RabbitmqMessage();
        message.setId(UUID.randomUUID().toString());
        message.setExchange(RabbitMQConstant.TEST_DEAD_LETTER_EXCHANGE_NAME);
        message.setRoutingKey(RabbitMQConstant.TEST_TOPIC_ROUTING_DL_KEY_R);
        message.setBody(jscode2Session);

        try {
            rabbitTemplateUtils.convertAndSend(message);
        } catch (Exception e) {
            log.error("deadLetterQueue",e);
        }

        return ResultModel.succWithData("success");
    }

}

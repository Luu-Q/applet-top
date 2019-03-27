package com.applet.controller.mq;

import com.applet.common.rabbitmq.RabbitMQConstant;
import com.applet.common.result.ResultModel;
import com.applet.common.utils.date.DateUtil;
import com.applet.entity.request.member.CrearteTokenReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Api(value = "rabbitmq", description = "rabbitmq测试接口")
@RestController
@RequestMapping("/rabbitmq")
public class MqController {


    @Autowired
    RabbitTemplate rabbitTemplate;

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
    public ResultModel<?> topicExchange() {

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        String currentDateTime = DateUtil.getCurrentDateTime();
        //路由
        rabbitTemplate.convertAndSend(RabbitMQConstant.TEST_TOPIC_EXCHANGE_NAME,RabbitMQConstant.TEST_TOPIC_ROUTING_KEY_A,currentDateTime,correlationData);
        //主题
        rabbitTemplate.convertAndSend(RabbitMQConstant.TEST_TOPIC_EXCHANGE_NAME,RabbitMQConstant.TEST_TOPIC_ROUTING_KEY,currentDateTime);
        //随便定义的前缀的key
        rabbitTemplate.convertAndSend(RabbitMQConstant.TEST_TOPIC_EXCHANGE_NAME,RabbitMQConstant.TEST_TOPIC_ROUTING_KEY+"me",currentDateTime);

        return ResultModel.succWithData("success");
    }

}

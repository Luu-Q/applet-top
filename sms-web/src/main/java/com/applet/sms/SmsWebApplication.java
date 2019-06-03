package com.applet.sms;

import com.applet.amqp.config.RabbitConfig;
import com.applet.common.springcontext.SpringContextHolder;
import com.applet.common.timer.ServiceInstanceService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableScheduling
@EnableSwagger2
@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.applet.*.dao")
@Import({RabbitConfig.class, SpringContextHolder.class, ServiceInstanceService.class})
public class SmsWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsWebApplication.class, args);
    }
}

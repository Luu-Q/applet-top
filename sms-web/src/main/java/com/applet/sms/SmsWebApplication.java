package com.applet.sms;

import com.applet.common.rabbitmq.RabbitConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

@EnableEurekaClient
@SpringBootApplication
@Import({RabbitConfig.class})
public class SmsWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsWebApplication.class, args);
    }
}

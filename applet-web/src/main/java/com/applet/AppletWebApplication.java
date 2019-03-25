package com.applet;

import com.applet.common.rabbitmq.RabbitConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@RibbonClient(name="clean-sms",configuration = RibbonIRuleConfig.class)
@EnableHystrix
@EnableFeignClients
@EnableEurekaClient
@EnableSwagger2
@SpringBootApplication(scanBasePackages = "com.applet.*")
@MapperScan("com.applet.*.dao")
@Import({RabbitConfig.class})
public class AppletWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppletWebApplication.class, args);
    }
}

package com.applet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@RibbonClient(name="clean-sms",configuration = RibbonIRuleConfig.class)
@EnableHystrix
@EnableEurekaClient
@EnableSwagger2
@SpringBootApplication(scanBasePackages = "com.clean.*")
@MapperScan("com.clean.applet.*.dao")
public class AppletApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppletApplication.class, args);
    }
}

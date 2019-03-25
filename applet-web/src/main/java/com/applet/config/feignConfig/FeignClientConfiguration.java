package com.applet.config.feignConfig;

import feign.Retryer;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {"com.clean.*.client"})
public class FeignClientConfiguration {
    
    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;
    
    @Bean
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }
    
    @Bean
    Retryer feignRetryer() {
        //FeignClient的重试次数，重试间隔为100ms，最大重试时间为1s,重试次数为5次
        //return new Retryer.Default(100, SECONDS.toMillis(1), 5);
        return Retryer.NEVER_RETRY;
    }
}

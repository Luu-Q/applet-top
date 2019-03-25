package com.applet.sms.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = "classpath:sms-config.properties")
@ConfigurationProperties(prefix = "sms")
public class SmsConfigBean {

    private String key;
    private String enp;
}

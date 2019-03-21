package com.applet.config.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class JedisPropertes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String host;
    private int port;
    private String password;
    private Integer maxTotal;
    private Integer maxIdle;
    private Integer minIdle;
    private Long maxWaitMillis;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean testWhileIdle;
}

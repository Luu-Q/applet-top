package com.applet.amqp.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: todo
 * @author: LUNING
 * @create: 2019-04-17 15:35
 */
@ConfigurationProperties("spring.rabbit-base")
public class RabbitMqBaseProperties {

    private String host = "localhost";
    private String username;
    private String password;
    private int port = 5672;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }



}

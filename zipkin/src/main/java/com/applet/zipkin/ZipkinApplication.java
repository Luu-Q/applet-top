package com.applet.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
@Import(ZipkinConfig.class)
public class ZipkinApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinApplication.class, args);
    }

}

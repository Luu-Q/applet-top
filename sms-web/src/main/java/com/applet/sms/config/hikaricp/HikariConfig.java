package com.applet.sms.config.hikaricp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages="com.applet.*.dao.*",sqlSessionTemplateRef="sqlSessionTemplate")
public class HikariConfig {

    @Bean(name="dataSource")
    @ConfigurationProperties(prefix="spring.datasource.hikari")
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}

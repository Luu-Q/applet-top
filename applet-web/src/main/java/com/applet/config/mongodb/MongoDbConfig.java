package com.applet.config.mongodb;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//@Configuration
//@EnableMongoAuditing
@EnableMongoRepositories
public class MongoDbConfig extends AbstractMongoConfiguration {

    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private Environment environment;

    @Bean
    public MongoClientOptions mongoOptions() {
        return MongoClientOptions
                .builder()
                .maxConnectionIdleTime(60000)
                .build();
    }


    @Override
    protected String getDatabaseName() {
        String mongoUrl = environment.getProperty("spring.data.mongodb.uri");
        String arr[] = mongoUrl.split("/");
        String name = arr[arr.length - 1];
        return name;
    }

    @Bean
    public Mongo mongo() throws Exception {
        String mongoUrl = environment.getProperty("spring.data.mongodb.uri");
        MongoClientURI mongoClientURI = new MongoClientURI(mongoUrl);
        return new MongoClient(mongoClientURI);
    }

    @Override
    public MongoClient mongoClient() {
        return null;
    }

    // 重写mongoTemplate方法（上层抽象类已经默认实现了这个方法）
    @Override
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoDbFactory factory = mongoDbFactory();
        // 以下操作是配置mongodb的记录不保存_class这个字段
        MongoMappingContext mongoMappingContext = new MongoMappingContext();
        mongoMappingContext.setApplicationContext(appContext);
        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(factory), mongoMappingContext);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return new MongoTemplate(factory, converter);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
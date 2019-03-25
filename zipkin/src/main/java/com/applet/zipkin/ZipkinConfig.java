package com.applet.zipkin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin.storage.InMemoryStorage;
import zipkin.storage.StorageComponent;


@Configuration
public class ZipkinConfig {

    @Bean
    public StorageComponent inMemoryStorage() {
        InMemoryStorage.Builder builder = new InMemoryStorage.Builder();
        builder.maxSpanCount(2 << 8);
        builder.strictTraceId(true);
        return builder.build();
    }

}

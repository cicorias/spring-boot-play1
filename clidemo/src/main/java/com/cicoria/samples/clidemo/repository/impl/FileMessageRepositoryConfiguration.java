package com.cicoria.samples.clidemo.repository.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration//(proxyBeanMethods = false)
public class FileMessageRepositoryConfiguration {

    @Value(value = "${app.message.repository.file.location}" )
    private String location;

    @Bean
    //@ConfigurationProperties(prefix = "app.message.repository.file")
    public FileMessageRepository dataSource()
    {
        return new FileMessageRepository(this.location);
    }
}

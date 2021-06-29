package com.cicoria.samples.clidemo;

import com.cicoria.samples.clidemo.repository.impl.FileMessageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
public class SenderConfiguration {
    @Value(value = "${app.sender.baseUrl}" )
    private String baseUrl;

    @Bean
    public Sender sender()
    {
        return new Sender(this.baseUrl);
    }
}

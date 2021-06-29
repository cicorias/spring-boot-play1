package com.cicoria.samples.clidemo.sender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
public class MessageSenderConfiguration {
    @Value(value = "${app.messageSender.baseUrl}" )
    private String baseUrl;

    @Bean
    public MessageSender sender()
    {
        return new MessageSender(this.baseUrl);
    }
}

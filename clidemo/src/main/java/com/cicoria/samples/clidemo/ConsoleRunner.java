package com.cicoria.samples.clidemo;

import com.cicoria.samples.clidemo.model.Message;
import com.cicoria.samples.clidemo.repository.MessageRepository;
import com.cicoria.samples.clidemo.sender.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(ConsoleRunner.class);

    private final MessageRepository<Message, String> repository;
    private final MessageSender messageSender;

    public ConsoleRunner(MessageRepository<Message, String> repository, MessageSender messageSender)
    {
        this.repository = repository;
        this.messageSender = messageSender;
    }

    @Override
    @Order(value = 1)
    public void run(String... args) {

        for (Message message: repository.findAll()){
            log.info(message.toString());
            messageSender.Send(message);
        }

    }
}

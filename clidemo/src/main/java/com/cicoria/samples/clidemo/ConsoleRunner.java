package com.cicoria.samples.clidemo;

import com.cicoria.samples.clidemo.model.Message;
import com.cicoria.samples.clidemo.repository.MessageRepository;
import com.cicoria.samples.clidemo.sender.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(ConsoleRunner.class);

    private final MessageRepository<Message, String> repository;
    private final Sender sender;

    public ConsoleRunner(MessageRepository<Message, String> repository, Sender sender)
    {
        this.repository = repository;
        this.sender = sender;
    }

    @Override
    @Order(value = 1)
    public void run(String... args) {

        for (Message message: repository.findAll()){
            log.info(message.toString());
            sender.Send(message);
        }

    }
}

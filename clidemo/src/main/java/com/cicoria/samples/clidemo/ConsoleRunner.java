package com.cicoria.samples.clidemo;

import com.cicoria.samples.clidemo.model.Message;
import com.cicoria.samples.clidemo.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(ConsoleRunner.class);

    private final MessageRepository<Message, String> repository;

    public ConsoleRunner(MessageRepository<Message, String> repository)
    {
        this.repository = repository;
    }

    @Override
    @Order(value = 1)
    public void run(String... args) {

        var rv = repository.findOne("foo");//.getNext();
        log.info(rv.toString());

    }
}

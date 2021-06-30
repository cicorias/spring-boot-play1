package com.cicoria.samples.clidemo;

import com.cicoria.samples.clidemo.model.Message;
import com.cicoria.samples.clidemo.repository.MessageRepository;
import com.cicoria.samples.clidemo.sender.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnExpression("${app.message-sender.by-path:false}")
public class ConsoleRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(ConsoleRunner.class);
    private final MessageRepository<Message, String> repository;
    private final MessageSender messageSender;

    @Value(value = "${app.message-sender.think-time-milliseconds:500}")
    private int thinkTimeMs; // half a second default

    public ConsoleRunner(MessageRepository<Message, String> repository, MessageSender messageSender)
    {
        this.repository = repository;
        this.messageSender = messageSender;
    }

    @Override
    @Order(value = 1)
    public void run(String... args) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        int addedDelay = 0;
        // TODO: this could be the looping scheduler approach.
        for (Message message: repository.findAll()) {
            log.info("delay of {}ms sending: {}", addedDelay, message.toString());

            executorService.schedule(() -> {
                log.info("now sending: {}", message);
                messageSender.Send(message);
            }, addedDelay, TimeUnit.MILLISECONDS);

            addedDelay += thinkTimeMs;
        }
        executorService.shutdown();
     }
}

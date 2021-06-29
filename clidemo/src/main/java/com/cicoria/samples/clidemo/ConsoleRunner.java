package com.cicoria.samples.clidemo;

import com.cicoria.samples.clidemo.model.Message;
import com.cicoria.samples.clidemo.repository.MessageRepository;
import com.cicoria.samples.clidemo.sender.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    @Value(value = "${app.message-sender.think-time-milliseconds}")
    private int thinkTimeMs = 500; // half a second default

    @Override
    @Order(value = 1)
    public void run(String... args) {

        Runnable task1 = () -> {
            count++;
            System.out.println("Running...task1 - count : " + count);
            for (Message message: repository.findAll()) {
                log.info(message.toString());
                messageSender.Send(message);
            }
        };

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(
                Classname::someTask,
                0,
                delayInSeconds,
                TimeUnit.MILLISECONDS);
  /*      for (Message message: repository.findAll()){
            log.info(message.toString());
            messageSender.Send(message);
            sleep
        }*/

    }
}

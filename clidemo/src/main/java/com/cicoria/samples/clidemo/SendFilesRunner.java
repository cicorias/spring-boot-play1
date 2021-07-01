package com.cicoria.samples.clidemo;

import com.cicoria.samples.clidemo.model.Message;
import com.cicoria.samples.clidemo.model.MessageFilter;
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
public class SendFilesRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(SendFilesRunner.class);
    private final MessageRepository<Message, String> repository;
    private final MessageSender messageSender;
    private final MessageFilter messageFilter;

    // TODO: externalize message filter class
    // TODO: private...

    @Value(value = "${app.message-sender.think-time-milliseconds:500}")
    private int thinkTimeMs; // half a second default

    public SendFilesRunner(MessageRepository<Message, String> repository,
                           MessageSender messageSender,
                           MessageFilter messageFilter) {
        this.repository = repository;
        this.messageSender = messageSender;
        this.messageFilter = messageFilter;
    }

    @Override
    @Order(value = 1)
    public void run(String... args) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        int addedDelay = 0;
        // TODO: looping scheduler approach in alternate implementation to use
        // TODO: executorService.scheduleAtFixedRate
        for (Message message : repository.findAll()) {
            log.info("delay of {}ms sending: {}", addedDelay, message.toString());

            executorService.schedule(() -> {
                String parsedMessage = messageFilter.replace(message.getBody());
                log.info("now sending: {}", parsedMessage);
                try {
                    messageSender.Send(parsedMessage);
                } catch (Exception e) {
                    log.error("error during processing of message: {} with exception {} stack {}",
                            message, e.getMessage(), e.getStackTrace());
                }
            }, addedDelay, TimeUnit.MILLISECONDS);

            addedDelay += thinkTimeMs;
        }
        executorService.shutdown();
    }

}

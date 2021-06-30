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

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@ConditionalOnExpression("${app.message-sender.by-path:false}")
public class SendFilesRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(SendFilesRunner.class);
    private final MessageRepository<Message, String> repository;
    private final MessageSender messageSender;

    // TODO: externalize message filter class
    // TODO: private...

    @Value(value = "${app.message-sender.think-time-milliseconds:500}")
    private int thinkTimeMs; // half a second default

    public SendFilesRunner(MessageRepository<Message, String> repository, MessageSender messageSender) {
        this.repository = repository;
        this.messageSender = messageSender;
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
                String parsedMessage = replaceTokens2(message.getBody());
                log.info("now sending: {}", parsedMessage);
                try {
                    messageSender.Send(parsedMessage);
                } catch (Exception e) {
                    log.error("error during processing of message: {} with exception {}", message.toString(), e.getMessage());
                }
            }, addedDelay, TimeUnit.MILLISECONDS);

            addedDelay += thinkTimeMs;
        }
        executorService.shutdown();
    }

    String replaceTokens2(String text){
        // NOTE: raw regex \{\{\$(.+?)\}\} - use https://regex101.com/ to convert
        final String regex = "\\{\\{\\$(.+?)\\}\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        HashMap<String,Object> replacements = new HashMap<String,Object>();
        //populate the replacements map ...

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = new Date(System.currentTimeMillis());
        long ut1 = date.getTime() / 1000L;
        //long ut1 = Instant.now().getEpochSecond();
        replacements.put("timestamp", ut1);
        replacements.put("isoTimestamp", sdf.format(date));
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (matcher.find()) {
            Object replacement = replacements.get(matcher.group(1));
            builder.append(text.substring(i, matcher.start()));
            if (replacement == null)
                builder.append(matcher.group(0));
            else
                builder.append(replacement);
            i = matcher.end();
        }
        builder.append(text.substring(i, text.length()));
        return builder.toString();
    }
}

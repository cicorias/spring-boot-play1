package com.cicoria.samples.clidemo.sender;

import com.cicoria.samples.clidemo.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;


public class MessageSender {
    private static final Logger log = LoggerFactory.getLogger(MessageSender.class);
    private String baseUrl;
    //private final WebClient webClient;


    public MessageSender(String baseUrl) {
        this.baseUrl = baseUrl;
//
//        WebClient.Builder builder = WebClient.builder();
//        builder.baseUrl(baseUrl);
//        webClient = builder
//                .build();
    }

    public void Send(Message message) {
        WebClient webClient = WebClient.create(this.baseUrl);
        String url = baseUrl; // +"/";
        try {
            var resp = webClient.post().uri(url)
                    .bodyValue(message.getBody())
                    .header("Content-Type", "application/json")
                    .retrieve().toBodilessEntity().block();
            log.info(String.valueOf(resp.getStatusCode()));
        } catch (Exception exp) {
            log.info(exp.getMessage());
        }
    }


}

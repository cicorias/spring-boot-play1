package com.cicoria.samples.clidemo;

import com.cicoria.samples.clidemo.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;


public class Sender {
    private static final Logger log = LoggerFactory.getLogger(Sender.class);
    private String baseUrl;
    private WebClient webClient;


    public Sender(String baseUrl) {
        webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public void Send(Message message) {
        String url = baseUrl; // +"/";
        try {
            var resp = webClient.post().uri(url)
                    .bodyValue(message.getBody())
                    .header("Content-Type", "application/json")
                    .retrieve().toBodilessEntity().block();
            //.header("Authorization", token)
            //.retrieve() //.bodyToMono(Organization.class)
            //.block();
            log.info(String.valueOf(resp.getStatusCode()));
        } catch (Exception exp) {
            log.info(exp.getMessage());
        }
    }


}

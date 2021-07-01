package com.cicoria.samples.clidemo.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;


public class MessageSender {
    private static final Logger log = LoggerFactory.getLogger(MessageSender.class);
    private final String baseUrl;
    private final WebClient webClient;

    public MessageSender(String baseUrl) {
        this.baseUrl = baseUrl;
        this.webClient = WebClient
                .builder()
                .baseUrl(this.baseUrl)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    // TODO: conditional add authorization header name and key value
                    // TODO: custom or authorization httpHeaders.set("Authorization", "value");
                })
                .build();
    }

    public void Send(String message){
        try {
            var resp = webClient.post().uri(baseUrl)
                    .bodyValue(message)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            if (null != resp) {
                int statusCode = resp.getStatusCodeValue();
                if (statusCode / 100 == 2) {
                    log.info("success {} for {}",
                            statusCode, message);
                }
                else {
                    log.error("failed {} for {}",
                            statusCode, message);
                }
            }

        } catch (Exception exp) {
            log.info("failed on Send: with message: {} - ex: {}  stack: {}",
                    message, exp.getMessage(), exp.getStackTrace());
        }
    }
}

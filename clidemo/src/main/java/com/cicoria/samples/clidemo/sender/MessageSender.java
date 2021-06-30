package com.cicoria.samples.clidemo.sender;

import com.cicoria.samples.clidemo.model.Message;
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
                    httpHeaders.set("fish", "value");
                })
                .build();

    }

    public void Send(Message message) {
        try {
            var resp = webClient.post().uri(baseUrl)
                    .bodyValue(message.getBody())
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            if (null != resp)
                log.info(String.valueOf(resp.getStatusCode()));

        } catch (Exception exp) {
            log.info(exp.getMessage());
        }
    }
}

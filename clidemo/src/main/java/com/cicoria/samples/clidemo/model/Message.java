package com.cicoria.samples.clidemo.model;

import com.fasterxml.jackson.databind.JsonNode;

public class Message {
    private String body;
    private JsonNode json;


    public Message(String body) {
        this.body = body;
    }

    public Message(JsonNode json){
        this.json = json;
        this.body = json.toString();
    }


    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "body='" + this.getBody() + '\'' +
                '}';
    }
}

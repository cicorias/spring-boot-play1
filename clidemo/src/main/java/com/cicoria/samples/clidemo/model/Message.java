package com.cicoria.samples.clidemo.model;

public class Message {
    public String body;

    public Message(String body) {
        this.body = body;
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

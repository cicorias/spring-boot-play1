package com.cicoria.samples.clidemo.repository.impl;

import com.cicoria.samples.clidemo.model.Message;
import com.cicoria.samples.clidemo.repository.MessageRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.lang.annotation.Annotation;

@Configuration
@ComponentScan()
public class FileMessageRepository implements MessageRepository<Message, String> {
    @Override
    public Message findOne(String s) {
        return new Message("foo");
    }

    @Override
    public Message save(Message entity) {
        return null;
    }

    @Override
    public Iterable<Message> findAll() {
        return null;
    }
}

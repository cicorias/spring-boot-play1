package com.cicoria.samples.clidemo.repository.impl;

import com.cicoria.samples.clidemo.model.Message;
import com.cicoria.samples.clidemo.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileMessageRepository implements MessageRepository<Message, String> {
    private static final Logger log = LoggerFactory.getLogger(FileMessageRepository.class);

    private final String location;

    public FileMessageRepository(String location) {
        this.location = location;
        log.info("using location " + this.location);
    }

    @Override
    public Message findOne(String s) {
        log.info("using location " + this.location);
        return new Message("foo");
    }

    @Override
    public Iterable<Message> findAll()
    {
        throw new UnsupportedOperationException();
    }
}

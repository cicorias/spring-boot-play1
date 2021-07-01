package com.cicoria.samples.clidemo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Message {
    private static final Logger log = LoggerFactory.getLogger(Message.class);
    private final JsonNode json;
    private final String content;


    public Message(String contents, boolean strict){
        this(contents);
        if (strict && null == this.json){
            log.error("failed to form proper json - validate the contents");
        }

    }
    public Message(String contents){
        JsonNode temp;
        this.content = contents;
        try{
            ObjectMapper mapper = new ObjectMapper();
            temp = mapper.readTree(contents);
        }
        catch(JsonProcessingException ex){
            temp = null;
            log.warn("string read as invalid json - only stored as string object - OK if template");
        }
        this.json = temp;
    }
    public Message(JsonNode json){
        if (null == json )
            throw new IllegalArgumentException("need a non null JsonNode");
        this.json = json;
        this.content = this.json.toPrettyString();
    }

    public String getBody() {
        if (null != json)
            return json.toString();
        return this.content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "body='" + this.getBody() + '\'' +
                '}';
    }
}

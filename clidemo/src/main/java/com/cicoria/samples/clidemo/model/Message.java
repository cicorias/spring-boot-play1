package com.cicoria.samples.clidemo.model;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Message {
    private final JsonNode json;
    private final String content;

    public Message(String contents){
        JsonNode temp;
        this.content = contents;
        try{
            ObjectMapper mapper = new ObjectMapper();
            temp = mapper.readTree(contents);
        }
        catch(JsonProcessingException ex){
            temp = null;
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

    public JsonNode getRoot() {
        return json;
    }

    @Override
    public String toString() {
        return "Message{" +
                "body='" + this.getBody() + '\'' +
                '}';
    }
}

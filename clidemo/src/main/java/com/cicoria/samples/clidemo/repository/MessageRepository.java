package com.cicoria.samples.clidemo.repository;

import com.cicoria.samples.clidemo.model.Message;

import java.io.Serializable;

//@org.springframework.stereotype.Repository
public interface MessageRepository<T, ID extends Serializable> { //extends Repository<T, ID> {
    T findOne(ID id);
    //T save(T entity);
    Iterable<Message> findAll() throws Exception;
}

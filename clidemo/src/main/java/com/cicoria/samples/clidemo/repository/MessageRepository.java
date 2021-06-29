package com.cicoria.samples.clidemo.repository;

//import org.springframework.data.repository.Repository;
import com.cicoria.samples.clidemo.model.Message;

import java.io.Serializable;
import java.util.Set;

//@org.springframework.stereotype.Repository
public interface MessageRepository<T, ID extends Serializable> { //extends Repository<T, ID> {
    T findOne(ID id);
    //T save(T entity);
    //Iterable<T> findAll();
    Iterable<Message> findAll();
}

package com.cicoria.samples.clidemo.repository;

//import org.springframework.stereotype.Repository;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
//import org.springframework.data.repository.CrudRepository;

//@Repository
//public interface MessageRepository extends Repository<> {
//    Message getNext();
//}
@org.springframework.stereotype.Repository
public interface MessageRepository<T, ID extends Serializable> extends Repository<T, ID> {
    T findOne(ID id);
    T save(T entity);
    Iterable<T> findAll();
}

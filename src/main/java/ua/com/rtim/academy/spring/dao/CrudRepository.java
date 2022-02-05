package ua.com.rtim.academy.spring.dao;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {

    List<T> findAll();

    void create(T entity);

    Optional<T> getById(int id);

    void update(T entity);

    void delete(int id);

}
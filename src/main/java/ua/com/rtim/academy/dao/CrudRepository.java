package ua.com.rtim.academy.dao;

import java.util.List;

public interface CrudRepository<T> {

    List<T> findAll();

    T create(T entity);

    T getById(int id);

    void update(T entity);

    void delete(int id);

}
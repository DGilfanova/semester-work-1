package ru.kpfu.itis.repositories;

import ru.kpfu.itis.entities.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CrudRepository<T> {

    void save(T entity);
    void update(T entity);
    void delete(Integer id);

    List<T> findAll(String id);
    Optional<Order> findById(Integer id);
}

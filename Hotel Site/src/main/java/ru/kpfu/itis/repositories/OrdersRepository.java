package ru.kpfu.itis.repositories;

import ru.kpfu.itis.entities.Order;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends CrudRepository<Order> {
    List<Order> findNewByUserUuid(String userUuid, Timestamp date);
    List<Order> findOldByUserUuid(String userUuid, Timestamp date);
    List<Order> findSameTimeOrder(Integer suggestionId, Timestamp date);
}

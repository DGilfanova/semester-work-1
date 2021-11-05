package ru.kpfu.itis.services;

import ru.kpfu.itis.dto.OrderForm;
import ru.kpfu.itis.dto.OrderUpdateForm;
import ru.kpfu.itis.entities.Order;
import ru.kpfu.itis.entities.Suggestion;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface OrderService {

    void orderSuggestion(OrderForm suggestionForm, HttpSession session);
    void update(OrderUpdateForm suggestionUpdateForm, HttpSession session);
    void delete(Integer orderedSuggestionId);
    List<Suggestion> findAllSuggestions();
    List<Order> findNewOrders(HttpSession session);
    List<Order> findOldOrders(HttpSession session);
    Order findById(Integer orderId);
}

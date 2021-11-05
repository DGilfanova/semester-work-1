package ru.kpfu.itis.helpers.processors;

import ru.kpfu.itis.dto.OrderForm;
import ru.kpfu.itis.entities.Order;
import ru.kpfu.itis.entities.Suggestion;
import ru.kpfu.itis.exceptions.EmptyFieldException;
import ru.kpfu.itis.exceptions.InvalidEnteredDataException;
import ru.kpfu.itis.helpers.constants.Constants;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderInfoProcessor implements InfoProcessor<Order, OrderForm> {

    @Override
    public Order process(OrderForm orderForm) {
        Order order = Order.builder()
                .suggestion(Suggestion.builder().id(processSuggestion(orderForm.getSuggestionId())).build())
                .build();

        processOrderDate(order, orderForm.getDate());
        return order;
    }

    private Integer processSuggestion(String  suggestionId) {
        if (suggestionId.equals("")) {
            throw new EmptyFieldException("Please choose suggestion.");
        }

        return Integer.valueOf(suggestionId);
    }

    private void processOrderDate(Order order, String date) {
        if (date.equals("")) {
            throw new EmptyFieldException("Please enter date.");
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.ORDER_DATE_FORMAT);
        Timestamp newDate;
        try {
            newDate = new Timestamp(simpleDateFormat.parse(date).getTime());
        } catch (ParseException e) {
            throw new InvalidEnteredDataException("Choose date from calendar, don't write yourself", e);
        }

        if (newDate.before(new Date())) {
            throw new InvalidEnteredDataException("Don't choose date in the past.");
        }

        order.setDate(newDate);
    }
}

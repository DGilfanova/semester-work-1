package ru.kpfu.itis.services;

import ru.kpfu.itis.dto.OrderForm;
import ru.kpfu.itis.dto.OrderUpdateForm;
import ru.kpfu.itis.entities.Order;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.exceptions.InvalidEnteredDataException;
import ru.kpfu.itis.helpers.constants.Constants;
import ru.kpfu.itis.entities.Suggestion;
import ru.kpfu.itis.helpers.processors.InfoProcessor;
import ru.kpfu.itis.helpers.notifiers.Notifier;
import ru.kpfu.itis.repositories.BookingsRepository;
import ru.kpfu.itis.repositories.OrdersRepository;
import ru.kpfu.itis.repositories.SuggestionsRepository;
import ru.kpfu.itis.repositories.UsersRepository;
import ru.kpfu.itis.helpers.notifiers.MailMessageTemplate;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {

    private SuggestionsRepository suggestionsRepository;

    private OrdersRepository ordersRepository;

    private UsersRepository usersRepository;

    private BookingsRepository bookingsRepository;

    private Notifier notifier;

    private MailMessageTemplate mailMessageTemplate;

    private InfoProcessor orderInfoProcessor;
    private InfoProcessor orderUpdateInfoProcessor;

    public OrderServiceImpl(SuggestionsRepository suggestionsRepository, OrdersRepository ordersRepository,
                            UsersRepository usersRepository, BookingsRepository bookingsRepository, Notifier notifier,
                            MailMessageTemplate mailMessageTemplate, InfoProcessor orderInfoProcessor, InfoProcessor orderUpdateInfoProcessor) {
        this.suggestionsRepository = suggestionsRepository;
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
        this.bookingsRepository = bookingsRepository;
        this.notifier = notifier;
        this.mailMessageTemplate = mailMessageTemplate;
        this.orderInfoProcessor = orderInfoProcessor;
        this.orderUpdateInfoProcessor = orderUpdateInfoProcessor;
    }

    @Override
    public List<Suggestion> findAllSuggestions() {
        return suggestionsRepository.findAll();
    }

    @Override
    public void orderSuggestion(OrderForm orderForm, HttpSession session) {
        UUID userUuid = findUserUuid(session);

        Order order = (Order) orderInfoProcessor.process(orderForm);
        order.setUser(User.builder().uuid(userUuid).build());

        if (ordersRepository.findSameTimeOrder(order.getSuggestion().getId(), order.getDate()).size() > 0) {
            throw new InvalidEnteredDataException("This suggestion has already been ordered for the next hour");
        }

        Timestamp currentDate = new Timestamp(new Date().getTime());
        if (bookingsRepository.findBookingSatisfyingOrderDate(userUuid.toString(), order.getDate(), currentDate).size() != 0) {
            throw new InvalidEnteredDataException("You ordered the service not during your stay at the hotel");
        }

        ordersRepository.save(order);

        Optional<User> optionalWorker = usersRepository.findBySuggestionId(order.getSuggestion().getId());
        notifier.notify(optionalWorker.get().getEmail(), mailMessageTemplate.createAddSuggestionText(order.getId().toString()));
    }

    @Override
    public void update(OrderUpdateForm orderUpdateForm, HttpSession session) {
        Order order = (Order) orderUpdateInfoProcessor.process(orderUpdateForm);

        if (ordersRepository.findSameTimeOrder(order.getSuggestion().getId(), order.getDate()).size() > 0) {
            throw new InvalidEnteredDataException("This suggestion has been ordered for the next hour");
        }

        Timestamp currentDate = new Timestamp(new Date().getTime());
        if (bookingsRepository.findBookingSatisfyingOrderDate(findUserUuid(session).toString(), order.getDate(), currentDate).size() != 0) {
            throw new InvalidEnteredDataException("You ordered the service not during your stay at the hotel");
        }

        ordersRepository.update(order);

        Optional<User> optionalWorker = usersRepository.findBySuggestionId(order.getSuggestion().getId());
        if (!optionalWorker.isPresent()) {
            throw new InvalidEnteredDataException("This suggestion is no longer available. We offer our apologies.");
        }

        notifier.notify(optionalWorker.get().getEmail(), mailMessageTemplate.createUpdateSuggestionText(order.getId().toString()));
    }

    @Override
    public void delete(Integer orderId) {
        ordersRepository.delete(orderId);
    }

    @Override
    public List<Order> findOldOrders(HttpSession session) {
        UUID userUuid = findUserUuid(session);
        Timestamp date = new Timestamp(new Date().getTime());

        return ordersRepository.findOldByUserUuid(userUuid.toString(), date);
    }

    @Override
    public List<Order> findNewOrders(HttpSession session) {
        UUID userUuid = findUserUuid(session);
        Timestamp date = new Timestamp(new Date().getTime());

        return ordersRepository.findNewByUserUuid(userUuid.toString(), date);
    }

    @Override
    public Order findById(Integer orderId) {
        Optional<Order> optionalOrder = ordersRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        }
        else {
            throw new InvalidEnteredDataException("There are some problems with your order. Contact the reception.");
        }
    }

    private UUID findUserUuid(HttpSession session) {
        User user = (User) session.getAttribute(Constants.USER_SESSION_ATTRIBUTE_NAME);
        return user.getUuid();
    }
}

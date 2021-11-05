package ru.kpfu.itis.servlets;

import ru.kpfu.itis.dto.OrderForm;
import ru.kpfu.itis.entities.Suggestion;
import ru.kpfu.itis.exceptions.EmptyFieldException;
import ru.kpfu.itis.exceptions.InvalidEnteredDataException;
import ru.kpfu.itis.exceptions.MailMessageException;
import ru.kpfu.itis.helpers.constants.Constants;
import ru.kpfu.itis.services.OrderService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ordersuggestion")
public class OrderSuggestionServlet extends HttpServlet {

    private OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        orderService = (OrderService) servletContext.getAttribute("orderService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Suggestion> suggestions = orderService.findAllSuggestions();
        request.setAttribute("suggestions", suggestions);
        request.getRequestDispatcher("/WEB-INF/views/orderSuggestion.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OrderForm orderForm = OrderForm.builder()
                .suggestionId(request.getParameter("sugId"))
                .date(request.getParameter("date"))
                .build();

        try {
            orderService.orderSuggestion(orderForm, request.getSession());

            response.sendRedirect(getServletContext().getContextPath() + "/showorders");
        }
        catch (InvalidEnteredDataException | EmptyFieldException | MailMessageException e) {
            request.setAttribute(Constants.ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.setAttribute("form", orderForm);
            List<Suggestion> suggestions = orderService.findAllSuggestions();
            request.setAttribute("suggestions", suggestions);
            request.getRequestDispatcher("/WEB-INF/views/orderSuggestion.jsp").forward(request, response);
        }
    }
}

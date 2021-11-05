package ru.kpfu.itis.servlets;

import ru.kpfu.itis.dto.OrderUpdateForm;
import ru.kpfu.itis.entities.Order;
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

@WebServlet("/editorder")
public class EditOrderServlet extends HttpServlet {

    private OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        orderService = (OrderService) servletContext.getAttribute("orderService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("id");
        if (orderId == null) {
            response.sendRedirect(request.getContextPath() + "/showorders?chooseOrderError=true");
            return;
        }

        Order order = orderService.findById(Integer.valueOf(orderId));
        request.setAttribute("order", order);
        request.getRequestDispatcher("/WEB-INF/views/editOrder.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OrderUpdateForm orderUpdateForm = OrderUpdateForm.builder()
                .orderId(request.getParameter("orderId"))
                .suggestionId(request.getParameter("suggestionId"))
                .date(request.getParameter("date"))
                .build();

        try {
            orderService.update(orderUpdateForm, request.getSession());

            response.sendRedirect(getServletContext().getContextPath() + "/showorders");
        }
        catch (InvalidEnteredDataException | EmptyFieldException | MailMessageException e) {
            request.setAttribute(Constants.ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.setAttribute("form", orderUpdateForm);
            Order order = orderService.findById(Integer.valueOf(orderUpdateForm.getOrderId()));
            request.setAttribute("order", order);
            request.getRequestDispatcher("/WEB-INF/views/editOrder.jsp").forward(request, response);
        }
    }
}

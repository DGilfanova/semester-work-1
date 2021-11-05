package ru.kpfu.itis.servlets;

import ru.kpfu.itis.services.OrderService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteorder")
public class DeleteOrderServlet extends HttpServlet {

    private OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        orderService = (OrderService) servletContext.getAttribute("orderService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("id");
        if (orderId == null) {
            response.sendRedirect(request.getContextPath() + "/showorders?chooseOrderError=true");
            return;
        }

        orderService.delete(Integer.valueOf(orderId));
        response.sendRedirect(request.getContextPath() + "/showorders");
    }
}

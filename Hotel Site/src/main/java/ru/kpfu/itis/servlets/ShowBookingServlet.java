package ru.kpfu.itis.servlets;

import ru.kpfu.itis.entities.Booking;
import ru.kpfu.itis.helpers.constants.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/showbooking")
public class ShowBookingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Booking booking = (Booking) request.getSession().getAttribute(Constants.BOOKING_ATTRIBUTE_NAME);
        if (booking == null) {
            response.sendRedirect(request.getContextPath() + "/retrievebooking?chooseBookingError=true");
            return;
        }

        request.getSession().removeAttribute(Constants.BOOKING_ATTRIBUTE_NAME);
        request.setAttribute(Constants.BOOKING_ATTRIBUTE_NAME, booking);
        request.getRequestDispatcher("/WEB-INF/views/showBooking.jsp").forward(request, response);
    }
}

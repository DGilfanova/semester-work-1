package ru.kpfu.itis.servlets;

import ru.kpfu.itis.dto.RetrieveBookingForm;
import ru.kpfu.itis.entities.Booking;
import ru.kpfu.itis.exceptions.EmptyFieldException;
import ru.kpfu.itis.exceptions.InvalidEnteredDataException;
import ru.kpfu.itis.exceptions.NoSuchElementInBaseException;
import ru.kpfu.itis.helpers.constants.Constants;
import ru.kpfu.itis.services.BookingService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/retrievebooking")
public class RetrieveBookingServlet extends HttpServlet {

    private BookingService bookingService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        bookingService = (BookingService) servletContext.getAttribute("bookingService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute(Constants.BOOKING_ATTRIBUTE_NAME, request.getSession().getAttribute(Constants.BOOKING_ATTRIBUTE_NAME));
        request.getSession().removeAttribute(Constants.BOOKING_ATTRIBUTE_NAME);
        request.setAttribute(Constants.CHOOSE_BOOKING_ERROR, request.getParameter(Constants.CHOOSE_BOOKING_ERROR));
        request.getRequestDispatcher("/WEB-INF/views/retrieveBooking.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RetrieveBookingForm retrieveBookingForm = RetrieveBookingForm.builder()
                .reservationNumber(request.getParameter("reservationNumber"))
                .email(request.getParameter("email"))
                .lastName(request.getParameter("lastName"))
                .build();

        try {
            Booking booking = bookingService.retrieveBooking(retrieveBookingForm);

            request.getSession().setAttribute(Constants.BOOKING_ATTRIBUTE_NAME, booking);
            response.sendRedirect(getServletContext().getContextPath() + "/showbooking");
        }
        catch (NoSuchElementInBaseException | InvalidEnteredDataException | EmptyFieldException e) {
            request.setAttribute(Constants.ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.setAttribute("form", retrieveBookingForm);
            request.getRequestDispatcher("/WEB-INF/views/retrieveBooking.jsp").forward(request, response);
        }
    }
}

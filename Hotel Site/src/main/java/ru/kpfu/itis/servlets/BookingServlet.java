package ru.kpfu.itis.servlets;

import ru.kpfu.itis.dto.BookingForm;
import ru.kpfu.itis.exceptions.EmptyFieldException;
import ru.kpfu.itis.exceptions.InvalidEnteredDataException;
import ru.kpfu.itis.entities.Room;
import ru.kpfu.itis.exceptions.MailMessageException;
import ru.kpfu.itis.helpers.constants.Constants;
import ru.kpfu.itis.services.BookingService;
import ru.kpfu.itis.services.RoomService;
import ru.kpfu.itis.services.SecurityService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {

    private RoomService roomService;

    private BookingService bookingService;

    private SecurityService securityService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        roomService = (RoomService) servletContext.getAttribute("roomService");
        bookingService = (BookingService) servletContext.getAttribute("bookingService");
        securityService = (SecurityService) servletContext.getAttribute("securityService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String  userUuid = (String) request.getSession().getAttribute(Constants.UUID_FOR_BOOKING_SESSION_ATTRIBUTE_NAME);
        if (userUuid == null) {
            response.sendRedirect(getServletContext().getContextPath() + "/registration?bookingUserError=true");
            return;
        }

        List<Room> rooms = roomService.findRooms();
        request.setAttribute("rooms", rooms);
        request.getRequestDispatcher("/WEB-INF/views/booking.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String  userUuid = (String) request.getSession().getAttribute(Constants.UUID_FOR_BOOKING_SESSION_ATTRIBUTE_NAME);
        if (userUuid == null) {
            response.sendRedirect(getServletContext().getContextPath() + "/registration?bookingUserError=true");
            return;
        }

        BookingForm bookingForm = BookingForm.builder()
                .roomNumber(request.getParameter("roomNumber"))
                .arrivalDate(request.getParameter("arrivalDate"))
                .departureDate(request.getParameter("departureDate"))
                .build();

        try {
            bookingService.createBooking(bookingForm, UUID.fromString(userUuid), request.getSession());

            request.getSession().removeAttribute(Constants.UUID_FOR_BOOKING_SESSION_ATTRIBUTE_NAME);
            request.getSession().setAttribute(Constants.BOOKING_ATTRIBUTE_NAME, true);
            response.sendRedirect(getServletContext().getContextPath() + "/retrievebooking");
        }
        catch (InvalidEnteredDataException | EmptyFieldException | MailMessageException e) {
            request.setAttribute(Constants.ERROR_ATTRIBUTE_NAME, e.getMessage());

            List<Room> rooms = roomService.findRooms();
            request.setAttribute("rooms", rooms);
            request.getRequestDispatcher("/WEB-INF/views/booking.jsp").forward(request, response);
        }
    }
}

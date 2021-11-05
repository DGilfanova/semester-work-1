package ru.kpfu.itis.servlets;

import ru.kpfu.itis.dto.UserInfoForm;
import ru.kpfu.itis.exceptions.*;
import ru.kpfu.itis.helpers.constants.Constants;
import ru.kpfu.itis.services.SecurityService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/edituser")
public class EditUserServlet extends HttpServlet {

    private SecurityService securityService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        securityService = (SecurityService) servletContext.getAttribute("securityService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/views/editUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserInfoForm editForm = UserInfoForm.builder()
                .firstName(request.getParameter("firstName"))
                .lastName(request.getParameter("lastName"))
                .patronymic(request.getParameter("patronymic"))
                .passportNumber(request.getParameter("passportNumber"))
                .mobileNumber(request.getParameter("mobileNumber"))
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .build();

        try {
            UUID userUuid = securityService.updateUser(editForm);

            request.getSession().setAttribute(Constants.UUID_FOR_BOOKING_SESSION_ATTRIBUTE_NAME, userUuid.toString());
            response.sendRedirect(getServletContext().getContextPath() + "/booking");
        }
        catch (InvalidEnteredDataException | EmptyFieldException | NoSuchElementInBaseException e) {
            request.setAttribute(Constants.ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.setAttribute("form", editForm);
            request.getRequestDispatcher("/WEB-INF/views/editUser.jsp").forward(request, response);
        }
    }
}

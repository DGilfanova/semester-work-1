package ru.kpfu.itis.servlets;

import ru.kpfu.itis.dto.UserInfoForm;
import ru.kpfu.itis.exceptions.EmptyFieldException;
import ru.kpfu.itis.exceptions.InvalidEnteredDataException;
import ru.kpfu.itis.exceptions.WrongExistingUserInfoException;
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

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private SecurityService securityService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        securityService = (SecurityService) servletContext.getAttribute("securityService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute(Constants.BOOKING_USER_ERROR, request.getParameter(Constants.BOOKING_USER_ERROR));
        request.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserInfoForm userInfoForm = UserInfoForm.builder()
                .firstName(request.getParameter("firstName"))
                .lastName(request.getParameter("lastName"))
                .patronymic(request.getParameter("patronymic"))
                .passportNumber(request.getParameter("passportNumber"))
                .mobileNumber(request.getParameter("mobileNumber"))
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .build();

        try {
            UUID userUuid = securityService.autoRegistration(userInfoForm);

            request.getSession().setAttribute(Constants.UUID_FOR_BOOKING_SESSION_ATTRIBUTE_NAME, userUuid.toString());
            response.sendRedirect(getServletContext().getContextPath() + "/booking");
        }
        catch (InvalidEnteredDataException| EmptyFieldException e) {
            request.setAttribute(Constants.ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.setAttribute("form", userInfoForm);
            request.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(request, response);
        }
        catch (WrongExistingUserInfoException e) {
            request.setAttribute(Constants.ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.setAttribute(Constants.EDIT_PROFILE_ATTRIBUTE_NAME, "true");
            request.setAttribute("form", userInfoForm);
            request.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(request, response);
        }
    }
}

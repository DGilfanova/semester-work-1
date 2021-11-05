package ru.kpfu.itis.servlets;

import ru.kpfu.itis.dto.SignInForm;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.exceptions.EmptyFieldException;
import ru.kpfu.itis.exceptions.InvalidEnteredDataException;
import ru.kpfu.itis.exceptions.NoSuchElementInBaseException;
import ru.kpfu.itis.helpers.constants.Constants;
import ru.kpfu.itis.services.SecurityService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signin")
public class SignInServlet extends HttpServlet {

    private SecurityService securityService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        securityService = (SecurityService) servletContext.getAttribute("securityService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute(Constants.AUTH_ERROR, request.getSession().getAttribute(Constants.AUTH_ERROR));
        request.getSession().removeAttribute(Constants.AUTH_ERROR);
        request.setAttribute(Constants.ROLE_ERROR, request.getSession().getAttribute(Constants.ROLE_ERROR));
        request.getSession().removeAttribute(Constants.ROLE_ERROR);
        request.setAttribute("form", request.getParameter("form"));
        request.getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SignInForm signInForm = SignInForm.builder()
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .rememberMe(request.getParameter("rememberMe"))
                .build();

        try {
            User user = securityService.signIn(signInForm, request.getSession());

            if (user.getRole().equals(Constants.GUEST_ROLE)) {
                if (signInForm.getRememberMe() != null) {
                    Cookie authCookie = new Cookie(Constants.AUTH_USER_COOKIE_NAME, user.getUuid().toString());
                    authCookie.setMaxAge(Constants.COOKIE_MAX_AGE);
                    response.addCookie(authCookie);
                }
                response.sendRedirect(getServletContext().getContextPath() + "/profile");
            } else {
                request.getSession().setAttribute(Constants.ROLE_ERROR, true);
                response.sendRedirect(getServletContext().getContextPath() + "/signin");
            }
        }
        catch (NoSuchElementInBaseException | EmptyFieldException | InvalidEnteredDataException e) {
            request.setAttribute(Constants.ERROR_ATTRIBUTE_NAME, e.getMessage());
            request.setAttribute("form", signInForm);
            request.getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(request, response);
        }
    }
}

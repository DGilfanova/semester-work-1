package ru.kpfu.itis.servlets;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import ru.kpfu.itis.helpers.constants.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/views/welcome.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("retrieveBookingButton") != null) {
            response.sendRedirect(getServletContext().getContextPath() + "/retrievebooking");
        }
        if (request.getParameter("signinButton") != null) {
            response.sendRedirect(getServletContext().getContextPath() + "/signin");
        }
        if (request.getParameter("profileButton") != null) {
            response.sendRedirect(getServletContext().getContextPath() + "/profile");
        }
    }
}

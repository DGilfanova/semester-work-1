package ru.kpfu.itis.filters;

import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.helpers.constants.Constants;
import ru.kpfu.itis.services.SecurityService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private String[] protectedPaths;

    private SecurityService securityService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedPaths = new String[]{"/profile", "/showorders", "/ordersuggestion", "/deleteorder", "/orderhistory",
                "/editorder", "showbooking"};
        securityService = (SecurityService) filterConfig.getServletContext().getAttribute("securityService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        if(securityService.isAuthenticated(session, request)) {
            User user = (User) session.getAttribute(Constants.USER_SESSION_ATTRIBUTE_NAME);
            String userRole = securityService.findUserRole(user.getUuid().toString());
            if (userRole.equals("guest")) {
                request.setAttribute(Constants.AUTH_ATTRIBUTE_NAME, true);
                filterChain.doFilter(request, response);
            } else {
                securityService.logout(request, response, session);
                request.getSession().setAttribute(Constants.ROLE_ERROR, true);
                response.sendRedirect(request.getContextPath() + "/signin");
            }
        }
        else {
            boolean isProtected = false;
            for(String s : protectedPaths){
                if(request.getRequestURI().startsWith(request.getContextPath() + s)){
                    isProtected = true;
                }
            }
            if(!isProtected){
                filterChain.doFilter(request, response);
                return;
            }
            request.getSession().setAttribute(Constants.ROLE_ERROR, true);
            response.sendRedirect(request.getContextPath() + "/signin");
        }
    }

    @Override
    public void destroy() {}
}

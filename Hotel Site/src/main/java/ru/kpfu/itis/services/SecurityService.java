package ru.kpfu.itis.services;

import ru.kpfu.itis.dto.UserInfoForm;
import ru.kpfu.itis.dto.SignInForm;
import ru.kpfu.itis.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

public interface SecurityService {

    UUID autoRegistration(UserInfoForm userInfoForm);

    User signIn(SignInForm signInForm, HttpSession session);

    boolean isAuthenticated(HttpSession session, HttpServletRequest request);

    void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session);

    UUID updateUser(UserInfoForm editForm);

    String findUserRole(String  userUuid);
}

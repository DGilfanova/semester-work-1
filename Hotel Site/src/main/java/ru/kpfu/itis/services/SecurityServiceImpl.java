package ru.kpfu.itis.services;

import org.apache.commons.codec.digest.DigestUtils;
import ru.kpfu.itis.dto.UserInfoForm;
import ru.kpfu.itis.dto.SignInForm;
import ru.kpfu.itis.exceptions.EmptyFieldException;
import ru.kpfu.itis.exceptions.InvalidEnteredDataException;
import ru.kpfu.itis.exceptions.NoSuchElementInBaseException;
import ru.kpfu.itis.exceptions.WrongExistingUserInfoException;
import ru.kpfu.itis.helpers.constants.Constants;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.helpers.processors.InfoProcessor;
import ru.kpfu.itis.repositories.UsersRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.UUID;

public class SecurityServiceImpl implements SecurityService {

    private UsersRepository usersRepository;

    private InfoProcessor userInfoProcessor;

    public SecurityServiceImpl(UsersRepository usersRepository, InfoProcessor userInfoProcessor) {
        this.usersRepository = usersRepository;
        this.userInfoProcessor = userInfoProcessor;
    }

    @Override
    public UUID autoRegistration(UserInfoForm userInfoForm) {
        UUID userUuid;
        User user = (User) userInfoProcessor.process(userInfoForm);
        Optional<User> optionalUser = usersRepository.findByEmail(user.getEmail());

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            if (existingUser.getHashPassword().equals(user.getHashPassword())) {
                if(existingUser.getFirstName().equals(user.getFirstName())
                        && existingUser.getLastName().equals(user.getLastName())
                        && existingUser.getPatronymic().equals(user.getPatronymic())
                        && existingUser.getMobileNumber().equals(user.getMobileNumber())
                        && existingUser.getPassportNumber().equals(user.getPassportNumber())) {
                    userUuid = existingUser.getUuid();
                }
                else {
                    throw new WrongExistingUserInfoException("We are glad that you have decided to relax in our hotel again, " +
                            "but your personal data does not match. You can change it.");
                }
            }
            else {
                throw new InvalidEnteredDataException("We know you. Enter the correct password to confirm your identity.");
            }
        }
        else {
            userUuid = saveUser(user);
        }
        return userUuid;
    }

    public UUID saveUser(User user) {
        UUID uuid = UUID.randomUUID();
        user.setUuid(uuid);

        usersRepository.save(user);
        return uuid;
    }

    @Override
    public User signIn(SignInForm signInForm, HttpSession session) {
        if (signInForm.getEmail().equals("") || signInForm.getPassword().equals("")) {
            throw new EmptyFieldException("Please fill all fields.");
        }

        Optional<User> optionalUser = usersRepository.findByEmail(signInForm.getEmail());
        if (!optionalUser.isPresent()) {
            throw new NoSuchElementInBaseException("User with this  email doesn't exist") ;
        }

        User user = optionalUser.get();
        if (!user.getHashPassword().equals(DigestUtils.md5Hex(signInForm.getPassword()))) {
            throw new InvalidEnteredDataException("Password is wrong");
        }

        session.setAttribute(Constants.USER_SESSION_ATTRIBUTE_NAME, user);
        return user;
    }

    @Override
    public boolean isAuthenticated(HttpSession session, HttpServletRequest request) {
        if (session.getAttribute(Constants.USER_SESSION_ATTRIBUTE_NAME) != null) {
            return true;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals(Constants.AUTH_USER_COOKIE_NAME)) {
                    Optional<User> optionalUser = usersRepository.findByUuid(cookie.getValue());
                    if (optionalUser.isPresent()) {
                        session.setAttribute(Constants.USER_SESSION_ATTRIBUTE_NAME, optionalUser.get());
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        session.removeAttribute(Constants.USER_SESSION_ATTRIBUTE_NAME);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals(Constants.AUTH_USER_COOKIE_NAME)) {
                    cookie.setMaxAge(Constants.COOKIE_DELETE_AGE);
                    response.addCookie(cookie);
                }
            }
        }
    }

    @Override
    public UUID updateUser(UserInfoForm editForm) {
        User user = (User) userInfoProcessor.process(editForm);

        Optional<User> optionalUser = usersRepository.findByEmail(user.getEmail());
        if (!optionalUser.isPresent()) {
            throw new NoSuchElementInBaseException("User with this email doesn't exist") ;
        }

        if (!user.getHashPassword().equals(optionalUser.get().getHashPassword())) {
            throw new InvalidEnteredDataException("Password is wrong.");
        }

        user.setUuid(optionalUser.get().getUuid());
        usersRepository.update(user);
        return user.getUuid();
    }

    @Override
    public String findUserRole(String userUuid) {
        Optional<User> optionalUser = usersRepository.findByUuid(userUuid);
        return optionalUser.get().getRole();
    }
}

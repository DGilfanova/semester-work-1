package ru.kpfu.itis.helpers.processors;

import org.apache.commons.codec.digest.DigestUtils;
import ru.kpfu.itis.dto.UserInfoForm;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.exceptions.EmptyFieldException;
import ru.kpfu.itis.exceptions.InvalidEnteredDataException;
import ru.kpfu.itis.helpers.constants.Constants;

public class UserInfoProcessor implements InfoProcessor<User, UserInfoForm> {

    @Override
    public User process(UserInfoForm userInfoForm) {
        User user = User.builder()
                .firstName(processName(userInfoForm.getFirstName()))
                .lastName(processName(userInfoForm.getLastName()))
                .patronymic(processName(userInfoForm.getPatronymic()))
                .passportNumber(processPassportNumber(userInfoForm.getPassportNumber()))
                .mobileNumber(processMobileNumber(userInfoForm.getMobileNumber()))
                .email(processEmail(userInfoForm.getEmail()))
                .hashPassword(processPassword(userInfoForm.getPassword()))
                .build();

        return user;
    }

    private String processName(String name) {
        if (name.equals("")) {
            throw new EmptyFieldException("Please enter name.");
        }
        if (!name.matches(Constants.NAME_REGEX)) {
            throw new InvalidEnteredDataException("Please write info in Latin letters.");
        }

        return name;
    }

    private String processEmail(String email) {
        if (email.equals("")) {
            throw new EmptyFieldException("Please enter email.");
        }
        if (!email.matches(Constants.EMAIL_REGEX)) {
            throw new InvalidEnteredDataException("Please check email. Entered email doesn't exist.");
        }

        return email;
    }

    private String processPassword(String rawPassword) {
        if (rawPassword.equals("")) {
            throw new EmptyFieldException("Please enter password.");
        }
        if (!rawPassword.matches(Constants.PASSWORD_REGEX)) {
            throw new InvalidEnteredDataException("The password must contain more than 8 and less than 20 characters of the Latin alphabet and numbers.");
        }

        String hashPassword = DigestUtils.md5Hex(rawPassword);
        return hashPassword;
    }

    private String processMobileNumber(String mobileNumber) {
        if (mobileNumber.equals("")) {
            throw new EmptyFieldException("Please enter mobile number.");
        }
        if (!mobileNumber.matches(Constants.MOBILE_NUMBER_REGEX)) {
            throw new InvalidEnteredDataException("Please check your mobile phone. ");
        }

        return mobileNumber;
    }

    private String processPassportNumber(String passportNumber) {
        if (passportNumber.equals("")) {
            throw new EmptyFieldException("Please enter passport number.");
        }
        if (!passportNumber.matches(Constants.PASSPORT_NUMBER_REGEX)) {
            throw new InvalidEnteredDataException("Please check your passport number.");
        }

        return passportNumber;
    }
}

package ru.kpfu.itis.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.helpers.constants.Constants;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String passportNumber;
    private String mobileNumber;
    private String email;
    private String hashPassword;
    private String role = Constants.DEFAULT_USER_ROLE;
}

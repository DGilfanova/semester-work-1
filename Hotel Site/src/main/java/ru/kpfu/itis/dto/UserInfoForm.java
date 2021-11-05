package ru.kpfu.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoForm {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String passportNumber;
    private String mobileNumber;
    private String email;
    private String password;
}

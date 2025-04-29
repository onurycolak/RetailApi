package com.onur.retail.api.request;

import com.onur.retail.domain.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public abstract class UserBaseRequest {
    @NotBlank(message = "Name must be provided")
    private String name;
    @NotBlank(message = "Surname must be provided")
    private String surname;
    @Email
    @NotBlank(message = "Email must be provided")
    private String email;
    @NotBlank(message = "Password must be provided")
    private String password;
    @NotNull(message = "User type must be provided")
    private UserType userType;

    public UserBaseRequest() {}

    public UserBaseRequest(String name, String surname, String email, UserType userType, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

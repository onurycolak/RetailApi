package com.onur.retail.api.response;

import com.onur.retail.domain.User;
import com.onur.retail.domain.UserType;

import java.util.UUID;

public class UserSessionResponse {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private UserType userType;

    public static UserSessionResponse from(User user) {
        UserSessionResponse response = new UserSessionResponse();

        response.id = user.getId();
        response.name = user.getName();
        response.surname = user.getSurname();
        response.email = user.getEmail();
        response.userType = user.getUserType();

        return response;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public UserType getUserType() {
        return userType;
    }
}

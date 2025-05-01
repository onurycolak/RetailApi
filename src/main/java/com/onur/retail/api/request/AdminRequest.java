package com.onur.retail.api.request;

import com.onur.retail.domain.UserType;

public class AdminRequest extends UserBaseRequest{
    public AdminRequest(
            String name,
            String surname,
            String email,
            UserType userType,
            String password
    ) {
        super(name, surname, email, userType, password);
    }
}

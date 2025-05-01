package com.onur.retail.api.request;

import com.onur.retail.domain.UserType;
import jakarta.validation.constraints.NotBlank;

public class CustomerRequest extends UserBaseRequest{
    @NotBlank(message = "Address must be provided")
    private final String address;
    private final String phoneNumber;

    public CustomerRequest(
            String name,
            String surname,
            String email,
            UserType userType,
            String password,
            String phoneNumber,
            String address
    ) {
        super(name, surname, email, userType, password);

        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

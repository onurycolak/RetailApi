package com.onur.retail.api.request;

import com.onur.retail.domain.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class CustomerRequest extends UserBaseRequest{
    @NotBlank(message = "Address must be provided")
    String address;
    //TODO: Add patter validation
    @NotEmpty(message = "Phone number must be provided")
    String phoneNumber;

    public CustomerRequest() {}

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

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

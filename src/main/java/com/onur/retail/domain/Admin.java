package com.onur.retail.domain;

import jakarta.persistence.Entity;

@Entity
public class Admin extends User{
    public Admin(
            String name,
            String surname,
            String email,
            UserType userType,
            String password
    ) {
        super(name, surname, email, userType, password);
    }
}

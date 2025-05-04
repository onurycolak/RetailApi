package com.onur.retail.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Admins")
public class Admin extends User{
    public Admin() { super(); }

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

package com.onur.retail.domain;

import com.onur.retail.util.Validate;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
public abstract class User {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private Instant createDate;
    private Instant updateDate;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User() {}

    public User(
            String name,
            String surname,
            String email,
            UserType userType,
            String password) {
        Validate.validateString(name, surname, email, password);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.createDate = Instant.now();
        this.updateDate = Instant.now();
        this.password = password;
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        Validate.validateString(password); //TODO: ADD VALIDATE PASSWORD UTIL
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate() {
        this.updateDate = Instant.now();
    }

    public UUID getId() {
        return id;
    }
}

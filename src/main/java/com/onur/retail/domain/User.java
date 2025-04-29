package com.onur.retail.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.UUID;

@Entity
public abstract class User {
    @Id
    @GeneratedValue
    private UUID id;
    @NotBlank(message = "Name must be provided")
    private String name;
    @NotBlank(message = "Surname must be provided")
    private String surname;
    @Email
    @NotBlank(message = "Email must be provided")
    private String email;
    private Instant createDate;
    private Instant updateDate;
    @NotBlank(message = "Password must be provided")
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User() {}

    public User(String name, String surname, String email, UserType userType, String password) {
        validateRequiredFields(name, surname, email, password);
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

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    private void validateRequiredFields(
            String name,
            String surname,
            String email,
            String password
    ) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must be non-null, non-blank");
        }
        if (surname == null || surname.isBlank()) {
            throw new IllegalArgumentException("Surname must be non-null, non-blank");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email must be non-null, non-blank");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password must be non-null, non-blank");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
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

    public Instant getCreateDate() {
        return createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public UUID getId() {
        return id;
    }
}

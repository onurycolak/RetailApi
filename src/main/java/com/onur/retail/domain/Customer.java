package com.onur.retail.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends User{
    @NotBlank(message = "Address must be provided")
    String address;
    //TODO: Add pattern validation
    String phoneNumber;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    List<CartItem> cartItems = new ArrayList<>();
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    List<Order> orders = new ArrayList<>(); //TODO: create order entity

    public Customer() {}

    public Customer(
            String name,
            String surname,
            String email,
            UserType userType,
            String password,
            String phoneNumber,
            String address
    ) {
        super(name, surname, email, userType, password);

        validateRequiredFields(address);

        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    private void validateRequiredFields(String address) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address must be non-null, non-blank");
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<CartItem> getCartProducts() {
        return cartItems;
    }

    public void addItemToCart(CartItem item) {
        cartItems.add(item);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }
}

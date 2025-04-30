package com.onur.retail.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends User {
    String address;
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

        this.address = address;
        this.phoneNumber = phoneNumber;

        validateString(address);
        //TODO: Add pattern validation for phone number
    }

    private void validateString(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Provided value must be non-null, non-blank");
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        validateString(phoneNumber);
        //TODO: Add pattern validation for phone number
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        validateString(address);

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
        if (order == null) {
            throw new IllegalArgumentException("Order must not be null.");
        }
        if (!this.equals(order.getCustomer())) {
            throw new IllegalArgumentException("Order does not belong to this customer.");
        }

        orders.add(order);
    }
}

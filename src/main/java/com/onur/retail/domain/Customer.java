package com.onur.retail.domain;

import com.onur.retail.util.Validate;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends User {
    private String address;
    private String phoneNumber;
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private final List<Order> orders = new ArrayList<>(); //TODO: create order entity

    public Customer () { super(); }

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

        this.phoneNumber = phoneNumber;

        Validate.validateString(address);

        this.address = address;
        //TODO: Add pattern validation for phone number
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        Validate.validateString(phoneNumber);
        //TODO: Add pattern validation for phone number
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        Validate.validateString(address);

        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
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

package com.onur.retail.api.response;

import com.onur.retail.domain.Customer;

import java.util.List;

public class CustomerProfileResponse {
    private UserSessionResponse user;
    private String address;
    private String phoneNumber;
    private CartResponse cart;
    private List<OrderResponse> orders;

    public static CustomerProfileResponse from (Customer customer) {
        CustomerProfileResponse response = new CustomerProfileResponse();

        response.user = UserSessionResponse.from(customer);
        response.address = customer.getAddress();
        response.cart = CartResponse.from(customer.getCart());
        response.phoneNumber = customer.getPhoneNumber();
        response.orders = customer.getOrders().stream()
                .map(OrderResponse::from)
                .toList();

        return response;
    }

    public UserSessionResponse getUser() {
        return user;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CartResponse getCart() {
        return cart;
    }

    public List<OrderResponse> getOrders() {
        return orders;
    }
}

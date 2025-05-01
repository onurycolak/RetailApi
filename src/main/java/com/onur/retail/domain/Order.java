package com.onur.retail.domain;

import com.onur.retail.util.Validate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Order {
    @Id
    @GeneratedValue
    private UUID id;
    private String coupon;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    private String transactionId;
    Instant orderDate;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<CartItem> cartItems = new ArrayList<>();


    public Order() {}

    public Order(
            Customer customer,
            List<CartItem> cartItems,
            String coupon,
            PaymentType paymentType,
            String transactionId
    ) {
        this.coupon = coupon;

        if (paymentType == null) {
            throw new IllegalArgumentException("Payment method can not be null");
        }

        this.paymentType = paymentType;
        this.transactionId = transactionId;

        validateFields(customer, cartItems);
        this.customer = customer;
        this.cartItems = cartItems;
        this.orderDate = Instant.now();
    }

    private void validateFields(Customer customer, List<CartItem> cartItems) {
        if (customer == null || cartItems == null) {
            throw new IllegalArgumentException("customer or cartItems cannot be null");
        }

        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("cartItems cannot be empty");
        }
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        if (orderStatus == null) {
            throw new IllegalArgumentException("orderStatus cannot be null");
        }

        this.orderStatus = orderStatus;
    }

    public UUID getId() {
        return id;
    }

    public String getCoupon() {
        return coupon;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public Instant getOrderDate() {
        return orderDate;
    }
}

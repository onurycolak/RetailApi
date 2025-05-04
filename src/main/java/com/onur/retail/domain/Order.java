package com.onur.retail.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    private String transactionId;
    Instant orderDate;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderItem> orderItems = new ArrayList<>();
    private BigDecimal totalPaid;


    public Order() {}

    public Order(
            Customer customer,
            Cart cart,
            PaymentType paymentType,
            String transactionId
    ) {
        validateFields(customer, cart, paymentType);
        this.paymentType = paymentType;
        this.transactionId = transactionId;
        this.customer = customer;
        this.orderDate = Instant.now();
        this.customer.addOrder(this);
        cart.getCartItems().forEach((cartItem -> orderItems.add(new OrderItem(this, cartItem))));

        BigDecimal subtotal = orderItems.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.totalPaid = subtotal.add(cart.getShippingAmount());
    }

    private void validateFields(Customer customer, Cart cart, PaymentType paymentType) {
        if (customer == null || cart == null || paymentType == null) {
            throw new IllegalArgumentException("Provided value(s) must not be null");
        }
    }

    public BigDecimal getTotalPaid() {
        return totalPaid;
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

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}

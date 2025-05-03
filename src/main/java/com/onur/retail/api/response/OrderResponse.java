package com.onur.retail.api.response;

import com.onur.retail.domain.Order;
import com.onur.retail.domain.PaymentType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class OrderResponse {
    private UUID orderId;
    private String orderStatus;
    private PaymentType paymentType;
    private String transactionId;
    private Instant orderDate;
    private BigDecimal totalPaid;
    private List<OrderItemResponse> items;

    public static OrderResponse from(Order order) {
        OrderResponse response = new OrderResponse();
        response.transactionId = order.getTransactionId();
        response.orderId = order.getId();
        response.orderStatus = order.getOrderStatus().name();
        response.totalPaid = order.getTotalPaid();
        response.paymentType = order.getPaymentType();
        response.orderDate = order.getOrderDate();
        response.items = order.getOrderItems().stream()
                .map(OrderItemResponse::from)
                .toList();

        return response;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public BigDecimal getTotalPaid() {
        return totalPaid;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }
}

package com.onur.retail.api.request;

import com.onur.retail.domain.PaymentType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderRequest {
    private String coupon;
    @NotNull(message = "Payment method can not be null")
    private PaymentType paymentType;
    private String transactionId;
    @NotNull(message = "customerId can not be null")
    private UUID customerId;
    @NotEmpty(message = "Cart must contain at least one item")
    private List<String> cartItemIds = new ArrayList<>();


    public OrderRequest(){}

    public OrderRequest(
            UUID customerId,
            List<String> cartItemIds,
            String coupon,
            PaymentType paymentType,
            String transactionId
    ) {
        this.coupon = coupon;
        this.paymentType = paymentType;
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.cartItemIds = cartItemIds;
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

    public UUID getCustomerId() {
        return customerId;
    }

    public List<String> getCartItemIds() {
        return cartItemIds;
    }
}

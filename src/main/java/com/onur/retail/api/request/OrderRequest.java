package com.onur.retail.api.request;

import com.onur.retail.domain.PaymentType;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class OrderRequest {
    @NotNull(message = "Payment method can not be null")
    private PaymentType paymentType;
    private String transactionId;
    @NotNull(message = "customerId can not be null")
    private UUID customerId;
    @NotNull(message = "cartId can not be null")
    private UUID cartId;


    public OrderRequest(){}

    public OrderRequest(
            UUID customerId,
            UUID cartId,
            PaymentType paymentType,
            String transactionId
    ) {
        this.paymentType = paymentType;
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.cartId = cartId;
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

    public UUID getCartId() {
        return cartId;
    }
}

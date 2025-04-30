package com.onur.retail.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public class CartItemRequest {
    @NotNull
    UUID customerId;
    @NotNull
    UUID variationId;
    @Positive
    @NotNull
    Integer quantity;

    public CartItemRequest() {}

    public CartItemRequest(
            UUID customerId,
            UUID variationId,
            Integer quantity
    ) {
        this.customerId = customerId;
        this.variationId = variationId;
        this.quantity = quantity;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getVariationId() {
        return variationId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}

package com.onur.retail.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public class CartItemRequest {
    @NotNull(message = "customerId be provided")
    private UUID customerId;
    @NotNull(message = "variationId must be provided")
    private UUID variationId;
    @Positive(message = "quantity must be a positive number")
    @NotNull(message = "quantity must be provided")
    private Integer quantity;

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

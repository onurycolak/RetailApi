package com.onur.retail.api.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class CartRequest {
    @NotNull(message = "customerId must be provided")
    private UUID customerId;

    public CartRequest() {}

    public CartRequest(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}
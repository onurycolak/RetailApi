package com.onur.retail.api.response;

import com.onur.retail.domain.CartItem;
import com.onur.retail.domain.ProductVariant;

import java.math.BigDecimal;
import java.util.UUID;

public class CartItemResponse {
    private UUID id;
    private String productName;
    private String size;
    private String color;
    private Integer quantity;
    private BigDecimal total;
    private BigDecimal originalTotal;
    private String productImageUrl;
    private BigDecimal originalUnitPrice;
    private BigDecimal unitPrice;


    public static CartItemResponse from (CartItem cartItem) {
        CartItemResponse response = new CartItemResponse();
        ProductVariant product = cartItem.getProductVariant();

        response.id = cartItem.getId();
        response.productName = product.getProductGroup().getName();
        response.productImageUrl = product.getImageUrl();
        response.size = product.getSize();
        response.color = product.getColor();
        response.quantity = cartItem.getQuantity();
        response.total = cartItem.getTotal();
        response.originalTotal = cartItem.getOriginalTotal();
        response.originalTotal = product.getOriginalPrice();
        response.unitPrice = product.getPrice();

        return response;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getOriginalUnitPrice() {
        return originalUnitPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public String getProductName() {
        return productName;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal getOriginalTotal() {
        return originalTotal;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }
}

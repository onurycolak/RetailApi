package com.onur.retail.api.response;

import com.onur.retail.domain.OrderItem;

import java.math.BigDecimal;

public class OrderItemResponse {
    private String productName;
    private BigDecimal originalPrice;
    private BigDecimal price;
    private int quantity;
    private String size;
    private String color;
    private BigDecimal subtotal;

    public static OrderItemResponse from(OrderItem orderItem) {
        OrderItemResponse response = new OrderItemResponse();

        response.productName = orderItem.getProductName();
        response.originalPrice = orderItem.getOriginalPrice();
        response.price = orderItem.getPrice();
        response.quantity = orderItem.getQuantity();
        response.size = orderItem.getSize();
        response.color = orderItem.getColor();
        response.subtotal = orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));

        return response;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
}

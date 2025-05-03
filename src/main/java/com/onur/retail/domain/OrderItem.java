package com.onur.retail.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    private String productName;
    private BigDecimal originalPrice;
    private BigDecimal price;
    private Integer quantity;
    private String size;
    private String color;
    private UUID productId;

    public OrderItem() {}

    public OrderItem(Order order, CartItem cartItem) {
        if (cartItem == null || order == null) {
            throw new IllegalArgumentException("cartItem or order must not be null");
        }

        ProductVariant productVariant = cartItem.getProductVariant();

        this.order = order;
        this.productName = productVariant.getProductGroup().getName();
        this.originalPrice = productVariant.getOriginalPrice();
        this.price = productVariant.getPrice();
        this.quantity = cartItem.getQuantity();
        this.size = productVariant.getSize();
        this.color = productVariant.getColor();
        this.productId = productVariant.getId();
    }

    @Transient
    public BigDecimal getSubtotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public UUID getProductId() {
        return productId;
    }

    public UUID getId() {
        return id;
    }

    public Order getOrder() {
        return order;
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

    public Integer getQuantity() {
        return quantity;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }
}

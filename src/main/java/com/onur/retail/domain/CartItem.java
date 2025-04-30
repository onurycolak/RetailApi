package com.onur.retail.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
public class CartItem {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "variant_id", nullable = false)
    private ProductVariant productVariant;
    private Instant addDate;
    private Instant updateDate;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    private Integer quantity = 1;

    public CartItem(){}

    public CartItem(Customer customer, ProductVariant productVariant, Integer quantity) {
        validateFields(customer, productVariant, quantity);

        this.customer = customer;
        this.productVariant = productVariant;
        this.quantity = (quantity == null) ? 1 : quantity;
        this.addDate = Instant.now();
        this.updateDate = Instant.now();
    }

    private void validateFields(
            Customer customer,
            ProductVariant productVariant,
            Integer quantity
    ) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer must be a non-null");
        }

        if (productVariant == null) {
            throw new IllegalArgumentException("Product must be a non-null");
        }

        if (quantity != null && quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be a positive integer.");
        }
    }

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public Instant getAddDate() {
        return addDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must not be null positive integer");
        }

        this.quantity = quantity;
        this.updateDate = Instant.now();
    }
}

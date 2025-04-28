package com.onur.retail.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductVariantRequest {
    @NotBlank(message = "Product Url be provided")
    private String productUrl;
    @NotBlank(message = "Product image be provided")
    private String imageUrl;
    @PositiveOrZero(message = "Discounted Price must be >= 0")
    private BigDecimal price;
    @NotNull(message = "Price must be provided")
    @PositiveOrZero(message = "Price must be >= 0")
    private BigDecimal originalPrice;
    private String color;
    private String size;
    @PositiveOrZero(message = "Stock must be >= 0")
    @NotNull(message = "Stock must be provided")
    private Integer stock;
    Boolean isDefault;

    public  ProductVariantRequest() {};

    public  ProductVariantRequest(
            String productUrl,
            String imageUrl,
            BigDecimal price,
            BigDecimal originalPrice,
            Integer stock,
            String color,
            String size,
            Boolean isDefault
    ) {
        this.originalPrice = originalPrice;
        this.size = size;
        this.color = color;
        this.stock = stock;
        this.price = Objects.requireNonNullElse(price, originalPrice);
        this.isDefault = isDefault;
    };

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean isDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }
}

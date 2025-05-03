package com.onur.retail.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductVariantRequest {
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

    public ProductVariantRequest() {};

    public ProductVariantRequest(
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
        this.imageUrl = imageUrl;
    };

    public String getImageUrl() {
        return imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public Integer getStock() {
        return stock;
    }

    public Boolean getIsDefault() {
        return this.isDefault;
    }
}

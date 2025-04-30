package com.onur.retail.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
public class ProductVariant {
    @Id
    @GeneratedValue
    private UUID id;
    private String productUrl;
    private String imageUrl;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private String color;
    private String size;
    private Instant createDate;
    private Instant updateDate;
    private Integer stock;
    Boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private ProductGroup productGroup;

    public ProductVariant() {}

    public ProductVariant(
            String productUrl,
            String imageUrl,
            BigDecimal price,
            BigDecimal originalPrice,
            Integer stock,
            String color,
            String size,
            Boolean isDefault
    ) {
        validateRequiredFields(stock, originalPrice, productUrl, imageUrl);

        this.originalPrice = originalPrice;
        this.size = size;
        this.color = color;
        this.stock = stock;

        this.price = Objects.requireNonNullElse(price, originalPrice);

        this.createDate = Instant.now();
        this.updateDate = Instant.now();
        this.isDefault = isDefault;
    }

    private void validateRequiredFields(
            Integer stock,
            BigDecimal originalPrice,
            String productUrl,
            String imageUrl
    ) {
        if (stock == null || stock < 0) {
            throw new IllegalArgumentException("Stock must be non-null and >= 0");
        }
        if (originalPrice == null || originalPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Original price must be non-null and >= 0");
        }
        if (productUrl == null || productUrl.isBlank()) {
            throw new IllegalArgumentException("Product Url must be non-null, non-blank");
        }
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new IllegalArgumentException("Product image must be non-null, non-blank");
        }
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        if (stock == null || stock < 0) {
            throw new IllegalArgumentException("Stock must be non-null and >= 0");
        }

        this.stock = stock;
    }

    public ProductGroup getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
        if (originalPrice == null || originalPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Original price must be non-null and >= 0");
        }

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

    public Instant getCreateDate() {
        return createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

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

    public Boolean getIsDefault() {
        return this.isDefault;
    }

    public void setDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
}

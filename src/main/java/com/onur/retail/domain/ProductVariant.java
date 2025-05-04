package com.onur.retail.domain;

import com.onur.retail.util.Validate;
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
    private String imageUrl;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private String color;
    private String size;
    private Instant createDate;
    private Instant updateDate;
    private Integer stock;
    private Boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private ProductGroup productGroup;

    public ProductVariant() {}

    public ProductVariant(
            String imageUrl,
            BigDecimal price,
            BigDecimal originalPrice,
            Integer stock,
            String color,
            String size,
            Boolean isDefault
    ) {
        this.size = size;
        this.color = color;
        this.stock = stock;

        this.createDate = Instant.now();
        this.updateDate = Instant.now();
        this.isDefault = isDefault;

        Validate.validateString(imageUrl);
        Validate.validatePositiveInteger(stock);
        Validate.validateBigDecimal(originalPrice);

        this.imageUrl = imageUrl;

        this.originalPrice = originalPrice;
        this.price = Objects.requireNonNullElse(price, originalPrice);
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        Validate.validatePositiveInteger(stock);

        this.stock = stock;
    }

    public ProductGroup getProductGroup() {
        return productGroup;
    }

    void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        Validate.validateBigDecimal(price);

        if (price.compareTo(this.originalPrice) > 0) {
            throw new IllegalArgumentException("Price must be non-null, bigger than 0 and more than or equal to original price");
        }

        this.price = price;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        Validate.validateBigDecimal(originalPrice);

        this.originalPrice = originalPrice;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        Validate.validateString(color);

        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        Validate.validateString(size);

        this.size = size;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate() {
        this.updateDate = Instant.now();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        Validate.validateString(imageUrl);

        this.imageUrl = imageUrl;
    }

    public Boolean getIsDefault() {
        return this.isDefault;
    }

    public void setDefault(Boolean isDefault) {
        this.isDefault = isDefault;

    }
}

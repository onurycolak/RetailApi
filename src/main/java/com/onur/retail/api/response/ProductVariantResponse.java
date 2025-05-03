package com.onur.retail.api.response;

import com.onur.retail.domain.ProductGroup;
import com.onur.retail.domain.ProductVariant;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductVariantResponse {
    private String productImage;
    private UUID id;
    private BigDecimal originalPrice;
    private BigDecimal price;
    private Integer stock;
    private String color;
    private String size;
    private Boolean isDefault;
    private UUID groupId;

    public static ProductVariantResponse from (ProductVariant variant) {
        ProductVariantResponse response = new ProductVariantResponse();

        ProductGroup productGroup = variant.getProductGroup();

        response.id = variant.getId();
        response.isDefault = variant.getIsDefault();
        response.size = variant.getSize();
        response.color = variant.getColor();
        response.stock = variant.getStock();
        response.productImage = variant.getImageUrl();
        response.originalPrice = variant.getOriginalPrice();
        response.price = variant.getPrice();
        response.groupId = productGroup.getId();

        return response;
    }

    public String getProductImage() {
        return productImage;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public UUID getGroupId() {
        return groupId;
    }
}

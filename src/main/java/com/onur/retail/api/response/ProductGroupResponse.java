package com.onur.retail.api.response;

import com.onur.retail.domain.ProductGroup;

import java.util.List;
import java.util.UUID;

public class ProductGroupResponse {
    private String productName;
    private String description;
    private List<ProductVariantResponse> variants;
    private UUID id;

    public static ProductGroupResponse from (ProductGroup group) {
        ProductGroupResponse response = new ProductGroupResponse();

        response.productName = group.getName();
        response.description = group.getDescription();
        response.id = group.getId();
        response.variants = group.getVariants().stream()
                .map(ProductVariantResponse::from)
                .toList();

        return response;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public List<ProductVariantResponse> getVariants() {
        return variants;
    }

    public UUID getId() {
        return id;
    }
}

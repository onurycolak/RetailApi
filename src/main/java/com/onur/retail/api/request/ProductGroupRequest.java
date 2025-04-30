package com.onur.retail.api.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class ProductGroupRequest {
    @NotBlank(message = "Product name be provided")
    private String name;
    @NotBlank(message = "Product url be provided")
    private String description;
    @NotEmpty(message = "Created product must have at least 1 variant")
    @Valid
    List<ProductVariantRequest> variants;

    public ProductGroupRequest() {}

    public ProductGroupRequest(
            String name,
            String description,
            List<ProductVariantRequest> variants
    ) {
        this.name = name;
        this.description = description;
        this.variants = variants;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<ProductVariantRequest> getVariants() {
        return variants;
    }
}

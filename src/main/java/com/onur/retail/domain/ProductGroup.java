package com.onur.retail.domain;

import com.onur.retail.util.Validate;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class ProductGroup {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "productGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ProductVariant> variants = new ArrayList<>();
    private Instant createDate;
    private Instant updateDate;

    public ProductGroup() {}

    public ProductGroup(
            String name,
            String description,
            List<ProductVariant> variants
    ) {
        this.createDate = Instant.now();
        this.updateDate = Instant.now();

        Validate.validateString(name, description);

        this.name = name;
        this.description = description;

        addVariants(variants);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<ProductVariant> getVariants() {
        return variants;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        if (updateDate == null) {
            throw new IllegalArgumentException("Provided update date must be non-null");
        }

        this.updateDate = updateDate;
    }

    public void setName(String name) {
        Validate.validateString(name);

        this.name = name;
    }

    public void setDescription(String description) {
        Validate.validateString(description);

        this.description = description;
    }

    public void addVariants(List<ProductVariant> variants) {
        if (variants == null || variants.isEmpty()) {
            throw new IllegalArgumentException("Provided variants must be non-null, non-empty");
        }

        this.variants.addAll(variants);

        ProductVariant lastDefault = null;

        for (ProductVariant variant : variants) {
            variant.setUpdateDate();
            variant.setProductGroup(this);

            if (Boolean.TRUE.equals(variant.getIsDefault())) {
                lastDefault = variant;
            }
        }

        if (lastDefault != null) {
            setDefaultProduct(lastDefault);
        }

        this.updateDate = Instant.now();
    }

    public Instant getCreateDate() {
        return this.createDate;
    }

    public void setDefaultProduct(ProductVariant variant) {
        if (variant == null) {
            throw new IllegalArgumentException("Variant must not be null");
        }
        if (variant.getProductGroup() != this) {
            throw new IllegalArgumentException("Variant does not belong to this group.");
        }

        variants.forEach((groupVariant) -> groupVariant.setDefault(false));

        variant.setDefault(true);
    }

    public ProductVariant getDefaultProduct() {
        return variants.stream()
                .filter(ProductVariant::getIsDefault)
                .findFirst()
                .orElse(null);
    }

    public ProductGroup removeVariant(UUID variantId) {
        variants.removeIf(variant -> variant.getId().equals(variantId));

        return this;
    }
}

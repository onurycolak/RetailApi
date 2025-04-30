package com.onur.retail.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
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

    public ProductGroup() {};

    public ProductGroup(
            String name,
            String description,
            List<ProductVariant> variants
    ) {
        this.createDate = Instant.now();
        this.updateDate = Instant.now();

        validateString(name, description);

        this.name = name;
        this.description = description;

        setVariants(variants);
    };

    private void validateString(String... values) {
        if (!Arrays.stream(values).allMatch(value -> value != null && !value.isBlank())) {
            throw new IllegalArgumentException("Provided value(s) must be non-null, non-blank");
        }
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
        validateString(name);

        this.name = name;
    }

    public void setDescription(String description) {
        validateString(description);

        this.description = description;
    }

    public void setVariants(List<ProductVariant> variants) {
        if (variants == null || variants.isEmpty()) {
            throw new IllegalArgumentException("Provided variants must be non-null, non-empty");
        }

        this.variants.addAll(variants);

        variants.forEach((groupVariant) -> {
            groupVariant.setUpdateDate(Instant.now());
            groupVariant.setProductGroup(this);
        });

        this.updateDate = Instant.now();
    }

    public void addVariant(ProductVariant variant) {
        variants.add(variant);
        variant.setProductGroup(this);

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

        variants.forEach((groupVariant) ->
                groupVariant.setDefault(groupVariant.getId().equals(variant.getId())));
    }

    public ProductVariant getDefaultProduct() {
        return variants.stream()
                .filter(ProductVariant::getIsDefault)
                .findFirst()
                .orElse(null);
    }
}

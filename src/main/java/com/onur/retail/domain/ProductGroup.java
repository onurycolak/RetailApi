package com.onur.retail.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class ProductGroup {
    @Id
    @GeneratedValue
    private UUID id;
    @NotBlank(message = "Product name must be provided")
    private String name;
    @NotBlank(message = "Product url must be provided")
    private String description;
    @NotEmpty(message = "Created product must have at least 1 variant")
    @Valid
    @OneToMany(mappedBy = "productGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> variants = new ArrayList<>();
    private Instant createDate;
    private Instant updateDate;

    public ProductGroup() {};

    public ProductGroup(
            String name,
            String description,
            List<ProductVariant> variants
    ) {
        this.name = name;
        this.description = description;
        this.createDate = Instant.now();
        this.updateDate = Instant.now();
        this.variants = variants;
    };

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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVariants(List<ProductVariant> variants) {
        this.variants = variants;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public void addVariant(@Valid ProductVariant variant) {
        variants.add(variant);
        variant.setProductGroup(this);
    }

    public Instant getCreateDate() {
        return this.createDate;
    }

    public void setDefaultProduct(@Valid ProductVariant variant) {
        variants.forEach((groupVariant) ->
                groupVariant.setDefault(groupVariant.getId().equals(variant.getId())));
    }
}

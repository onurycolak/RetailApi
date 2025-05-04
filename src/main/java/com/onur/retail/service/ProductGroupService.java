package com.onur.retail.service;

import com.onur.retail.api.request.ProductGroupRequest;
import com.onur.retail.api.request.ProductVariantRequest;
import com.onur.retail.api.response.ProductGroupResponse;
import com.onur.retail.domain.ProductGroup;
import com.onur.retail.domain.ProductVariant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@ApplicationScoped
public class ProductGroupService {

    @Inject
    EntityManager entityManager;

    public ProductGroupResponse createGroup(ProductGroupRequest request) {
        ProductGroup group = new ProductGroup();
        group.setName(request.getName());
        group.setDescription(request.getDescription());

        List<ProductVariant> variants = request.getVariants().stream()
                .map(ProductVariantRequest::toEntity)
                .toList();

        group.setVariants(variants); // this will wire ownership + timestamps

        entityManager.persist(group); // cascades variants

        return ProductGroupResponse.from(group);
    }
}

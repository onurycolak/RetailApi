package com.onur.retail.service;

import com.onur.retail.api.request.ProductGroupRequest;
import com.onur.retail.api.request.ProductVariantRequest;
import com.onur.retail.api.response.ErrorResponse;
import com.onur.retail.api.response.ProductGroupResponse;
import com.onur.retail.api.response.ProductVariantResponse;
import com.onur.retail.domain.ProductGroup;
import com.onur.retail.domain.ProductVariant;

import com.onur.retail.repository.ProductGroupRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProductGroupService {

    @Inject
    EntityManager entityManager;

    @Inject
    ProductGroupRepository groupRepository;

    public ProductGroupResponse createGroup(ProductGroupRequest request) {
        ProductGroup group = new ProductGroup();
        group.setName(request.getName());
        group.setDescription(request.getDescription());

        List<ProductVariant> variants = request.getVariants().stream()
                .map(ProductVariantRequest::toEntity)
                .toList();

        group.addVariants(variants); // this will wire ownership + timestamps

        entityManager.persist(group); // cascades variants

        return ProductGroupResponse.from(group);
    }

    @Transactional
    public List<ProductVariantResponse> addVariants (UUID groupId, List<ProductVariantRequest> request) {
        ProductGroup group = groupRepository.findById(groupId);

        List<ProductVariant> variants = request.stream()
                .map(ProductVariantRequest::toEntity)
                .toList();

        group.addVariants(variants);

        entityManager.flush();

        return variants.stream()
                .map(ProductVariantResponse::from)
                .toList();
    }

    public ProductGroupResponse getGroup (UUID groupId) {
        ProductGroup group = groupRepository.findById(groupId);

        if (group == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse("Product group not found"))
                            .type("application/json")
                            .build()
            );
        }

        return ProductGroupResponse.from(group);
    }

    @Transactional
    public void deleteGroup (UUID groupId) {
        ProductGroup group = groupRepository.findById(groupId);

        if (group == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse("Product group not found"))
                            .type("application/json")
                            .build()
            );
        }

        entityManager.remove(group);
    }

    @Transactional
    public boolean updateGroup(UUID groupId, UUID oldDefaultId) {
        ProductGroup group = groupRepository.findById(groupId);

        ProductVariant newDefault = group.getVariants()
                .stream()
                .filter((productVariant -> !productVariant.getId().equals(oldDefaultId)))
                .findFirst()
                .orElse(null);

        if (newDefault == null) {
            return false;
        }

        group.setDefaultProduct(newDefault);

        return true;
    }
}

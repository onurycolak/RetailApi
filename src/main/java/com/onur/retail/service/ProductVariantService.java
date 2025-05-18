package com.onur.retail.service;

import com.onur.retail.api.response.ErrorResponse;
import com.onur.retail.api.response.ProductGroupResponse;
import com.onur.retail.api.response.ProductVariantResponse;
import com.onur.retail.domain.ProductGroup;
import com.onur.retail.domain.ProductVariant;
import com.onur.retail.repository.ProductGroupRepository;
import com.onur.retail.repository.ProductVariantRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@ApplicationScoped
public class ProductVariantService {
    @Inject
    EntityManager entityManager;

    @Inject
    ProductVariantRepository productVariantRepository;

    @Inject
    ProductGroupService groupService;

    public ProductVariantResponse getVariant (UUID variantId) {
        ProductVariant variant = productVariantRepository.findById(variantId);

        if (variant == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse("Variant not found"))
                            .type("application/json")
                            .build()
            );
        }

        return ProductVariantResponse.from(variant);
    }

    @Transactional
    public void deleteVariant (UUID variantId) {
        ProductVariant variant = productVariantRepository.findById(variantId);

        if (variant == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse("Variant not found"))
                            .type("application/json")
                            .build()
            );
        }

       ProductGroup group = variant.getProductGroup();

        if (group == null) {
            entityManager.remove(variant);
            entityManager.flush();

            return;
        }

        if (group.getVariants().size() == 1) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(new ErrorResponse("Cannot remove the only variant of a group."))
                            .type("application/json")
                            .build()
            );
        }

        if (variant.getIsDefault()) {
            if (!groupService.updateGroup(group.getId(), variantId)) {
                throw new WebApplicationException(
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(new ErrorResponse("Failed removing default item. No other eligible defaults"))
                                .type("application/json")
                                .build()
                );
            }
        }

        group.getVariants().remove(variant);
        entityManager.remove(variant);
        entityManager.flush();
    }
}

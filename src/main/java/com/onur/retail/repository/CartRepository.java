package com.onur.retail.repository;

import com.onur.retail.api.response.ErrorResponse;
import com.onur.retail.domain.Cart;
import com.onur.retail.domain.CartItem;
import com.onur.retail.domain.Customer;
import com.onur.retail.domain.ProductVariant;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@ApplicationScoped
public class CartRepository implements PanacheRepositoryBase<Cart, UUID> {
    @Inject
    EntityManager entityManager;
    @Inject
    CustomerRepository customerRepository;

    public CartItem findByVariantId(UUID variantId, UUID cartId) {
        return entityManager.createQuery(
                        "SELECT c FROM CartItem c WHERE c.cart.id = :cartId AND c.productVariant.id = :variantId", CartItem.class)
                .setParameter("cartId", cartId)
                .setParameter("variantId", variantId)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
}

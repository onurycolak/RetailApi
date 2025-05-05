package com.onur.retail.service;

import com.onur.retail.api.request.CartItemRequest;
import com.onur.retail.api.response.CartItemResponse;
import com.onur.retail.api.response.CartResponse;
import com.onur.retail.api.response.ErrorResponse;
import com.onur.retail.domain.CartItem;
import com.onur.retail.domain.Customer;
import com.onur.retail.domain.ProductVariant;
import com.onur.retail.repository.CartRepository;
import com.onur.retail.repository.CustomerRepository;
import com.onur.retail.repository.ProductVariantRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.Objects;

@ApplicationScoped
public class CartService {
    @Inject
    EntityManager entityManager;
    @Inject
    ProductVariantRepository productVariantRepository;
    @Inject
    CustomerRepository customerRepository;
    @Inject
    CartRepository cartRepository;

    public CartResponse addItemToCart(CartItemRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId());
        ProductVariant variant = productVariantRepository.findById(request.getVariationId());

        if (variant == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse("Item not found"))
                            .type("application/json")
                            .build()
            );
        }

        if (customer == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse("Customer not found"))
                            .type("application/json")
                            .build()
            );
        }

        CartItem existingCartItem = cartRepository.findByVariantId(variant.getId(), customer.getCart().getId());

        int existingQuantity = (existingCartItem == null) ? 0 : existingCartItem.getQuantity();
        int quantity = request.getQuantity() + existingQuantity;

        if (quantity > variant.getStock()) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(new ErrorResponse("Requested quantity exceeds available stock"))
                            .type("application/json")
                            .build()
            );
        }

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
        } else {
            CartItem cartItem = new CartItem(customer, variant, request.getQuantity());

            customer.getCart().addItemToCart(cartItem);

            entityManager.flush();
        }

        return CartResponse.from(customer.getCart());
    }
}

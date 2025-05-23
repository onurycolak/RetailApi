package com.onur.retail.service;

import com.onur.retail.api.request.CartItemRequest;
import com.onur.retail.api.response.CartResponse;
import com.onur.retail.api.response.ErrorResponse;
import com.onur.retail.domain.Cart;
import com.onur.retail.domain.CartItem;
import com.onur.retail.domain.Customer;
import com.onur.retail.domain.ProductVariant;
import com.onur.retail.repository.CartRepository;
import com.onur.retail.repository.CustomerRepository;
import com.onur.retail.repository.ProductVariantRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

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

    public Cart getCartByUserId(UUID customerId) {
        Customer customer = customerRepository.findById(customerId);

        if (customer == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse("User not found"))
                            .type("application/json")
                            .build()
            );
        }

        return customer.getCart();
    }

    @Transactional
    public void clearCartByCustomerId(UUID customerId) {
        Cart cart = getCartByUserId(customerId);

        cart.clearCart();

        entityManager.flush();
    }

    @Transactional
    public Cart removeItemFromCart(UUID customerId, UUID itemId, Integer quantity) {
        Cart cart = getCartByUserId(customerId);

        if (cart == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(new ErrorResponse("No cart associated with the user"))
                            .type("application/json")
                            .build()
            );
        }

        CartItem cartItem = cartRepository.findByVariantId(itemId, cart.getId());

        if (cartItem == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(new ErrorResponse("Variant could not be found in the cart"))
                            .type("application/json")
                            .build()
            );
        }

        Integer cartItemQuantity =  cartItem.getQuantity();

        if (quantity == null) {
            quantity = 1;
        }

        if (quantity <= 0) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(new ErrorResponse("Quantity cannot be negative integer"))
                            .type("application/json")
                            .build()
            );
        }

        if (quantity > cartItemQuantity) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(new ErrorResponse("Quantity to remove cannot exceed item quantity in cart"))
                            .type("application/json")
                            .build()
            );
        }

        cartItem.setQuantity(cartItemQuantity - quantity);

        entityManager.flush();

        return cart;
    }
}

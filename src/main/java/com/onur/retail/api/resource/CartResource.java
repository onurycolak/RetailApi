package com.onur.retail.api.resource;

import com.onur.retail.api.request.CartItemRequest;
import com.onur.retail.api.response.CartResponse;
import com.onur.retail.domain.Cart;
import com.onur.retail.repository.CartRepository;
import com.onur.retail.service.CartService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class CartResource {
    @Inject
    CartService cartService;
    @Inject
    CartRepository cartRepository;

    @POST
    @Path("/add")
    public Response addToCart(@Valid CartItemRequest request) {
        CartResponse created = cartService.addItemToCart(request);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    @Path("/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCart(@PathParam("customerId") UUID customerId) {
        Cart cart = cartService.getCartByUserId(customerId);

        return Response.ok(CartResponse.from(cart)).build();
    }

    @DELETE
    @Path("/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response emptyCart(@PathParam("customerId") UUID customerId) {
        cartService.clearCartByCustomerId(customerId);

        return Response.noContent().build();
    }

    @DELETE
    @Path("/{customerId}/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeItemFromCart(
            @PathParam("customerId") UUID customerId,
            @PathParam("itemId") UUID itemId,
            @QueryParam("q") Integer quantity
    ) {
        Cart updatedCart = cartService.removeItemFromCart(customerId, itemId, quantity);

        return Response.ok(CartResponse.from(updatedCart)).build();
    }

}

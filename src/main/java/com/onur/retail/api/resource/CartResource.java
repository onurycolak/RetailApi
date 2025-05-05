package com.onur.retail.api.resource;

import com.onur.retail.api.request.CartItemRequest;
import com.onur.retail.api.response.CartResponse;
import com.onur.retail.service.CartService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class CartResource {
    @Inject
    CartService cartService;

    @POST
    @Path("/add")
    public Response addToCart(@Valid CartItemRequest request) {
        CartResponse created = cartService.addItemToCart(request);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }
}

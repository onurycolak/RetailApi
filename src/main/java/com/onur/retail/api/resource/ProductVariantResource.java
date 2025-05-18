package com.onur.retail.api.resource;

import com.onur.retail.api.response.ProductGroupResponse;
import com.onur.retail.api.response.ProductVariantResponse;
import com.onur.retail.service.ProductGroupService;
import com.onur.retail.service.ProductVariantService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/product-variant")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class ProductVariantResource {
    @Inject
    ProductVariantService productVariantService;

    @GET
    @Path("/{variantId}")
    public Response getVariants(@PathParam("variantId") UUID variantId) {
        ProductVariantResponse response = productVariantService.getVariant(variantId);

        return Response.status(Response.Status.OK).entity(response).build();
    }

    @DELETE
    @Path("{variantId}")
    public Response deleteVariant(@PathParam("variantId") UUID variantId) {
        productVariantService.deleteVariant(variantId);

        return  Response.status(Response.Status.OK).build();
    }
}

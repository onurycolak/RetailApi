package com.onur.retail.api.resource;

import com.onur.retail.api.request.ProductGroupRequest;
import com.onur.retail.api.request.ProductVariantRequest;
import com.onur.retail.api.response.ProductGroupResponse;
import com.onur.retail.api.response.ProductVariantResponse;
import com.onur.retail.service.ProductGroupService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/product-group")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class ProductGroupResource {

    @Inject
    ProductGroupService productGroupService;

    @POST
    @Path("/create")
    public Response createProductGroup(@Valid ProductGroupRequest request) {
        ProductGroupResponse created = productGroupService.createGroup(request);

        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @POST
    @Path("/{groupId}/add-variants")
    public Response addVariantsToGroup(@PathParam("groupId") UUID groupId, @Valid List<ProductVariantRequest> requests) {
        List<ProductVariantResponse> created = productGroupService.addVariants(groupId, requests);

        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    @Path("/{groupId}")
    public Response getGroup(@PathParam("groupId") UUID groupId) {
        ProductGroupResponse response = productGroupService.getGroup(groupId);

        return Response.status(Response.Status.OK).entity(response).build();
    }

    @DELETE
    @Path("/{groupId}")
    public Response deleteGroup(@PathParam("groupId") UUID groupId) {
        System.out.println("test");
        productGroupService.deleteGroup(groupId);

        return  Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/delete-debug/{groupId}")
    public Response testDeletePath(@PathParam("groupId") UUID groupId) {
        System.out.println("HIT /delete-debug with ID = " + groupId);
        return Response.ok().build();
    }
}
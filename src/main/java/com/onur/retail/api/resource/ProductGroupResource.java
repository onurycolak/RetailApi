package com.onur.retail.api.resource;

import com.onur.retail.api.request.ProductGroupRequest;
import com.onur.retail.api.response.ProductGroupResponse;
import com.onur.retail.service.ProductGroupService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/product-groups")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class ProductGroupResource {

    @Inject
    ProductGroupService productGroupService;

    @POST
    public Response createProductGroup(@Valid ProductGroupRequest request) {
        ProductGroupResponse created = productGroupService.createGroup(request);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }
}
package com.onur.retail.api.resource;

import com.onur.retail.api.request.CustomerRequest;
import com.onur.retail.api.response.CustomerProfileResponse;
import com.onur.retail.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/create-customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class CustomerResource {

    @Inject
    CustomerService customerService;

    @POST
    public Response createProductGroup(@Valid CustomerRequest request) {
        CustomerProfileResponse created = customerService.createCustomer(request);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }
}

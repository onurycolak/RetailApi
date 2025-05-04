package com.onur.retail.service;

import com.onur.retail.api.request.CustomerRequest;
import com.onur.retail.api.response.CustomerProfileResponse;
import com.onur.retail.api.response.ErrorResponse;
import com.onur.retail.domain.Cart;
import com.onur.retail.domain.Customer;
import com.onur.retail.repository.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class CustomerService {

    @Inject
    EntityManager entityManager;
    @Inject
    CustomerRepository customerRepository;

    public CustomerProfileResponse createCustomer(CustomerRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(new ErrorResponse("Email already exists"))
                            .type("application/json")
                            .build()
            );
        }

        if (request.getPhoneNumber() != null && customerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(new ErrorResponse("Phone number already exists"))
                            .type("application/json")
                            .build()
            );
        }

        Customer customer = new Customer(
                request.getName(),
                request.getSurname(),
                request.getEmail(),
                request.getUserType(),
                request.getPassword(),
                request.getPhoneNumber(),
                request.getAddress()
        );

        Cart cart = new Cart(customer);

        customer.setCart(cart);

        entityManager.persist(customer);

        return CustomerProfileResponse.from(customer);
    }
}

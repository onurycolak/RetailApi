package com.onur.retail.repository;

import com.onur.retail.domain.Customer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CustomerRepository {

    @Inject
    EntityManager entityManager;

    public Optional<Customer> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(Customer.class, id));
    }

    public boolean existsByEmail(String email) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(c) FROM Customer c WHERE c.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return count > 0;
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(c) FROM Customer c WHERE c.phoneNumber = :phone", Long.class)
                .setParameter("phone", phoneNumber)
                .getSingleResult();
        return count > 0;
    }
}

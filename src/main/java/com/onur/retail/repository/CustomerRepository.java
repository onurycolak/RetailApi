package com.onur.retail.repository;

import com.onur.retail.domain.Customer;
import com.onur.retail.domain.ProductVariant;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CustomerRepository implements PanacheRepositoryBase<Customer, UUID> {

    @Inject
    EntityManager entityManager;

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

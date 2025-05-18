package com.onur.retail.repository;

import com.onur.retail.domain.ProductGroup;
import com.onur.retail.domain.ProductVariant;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class ProductGroupRepository implements PanacheRepositoryBase<ProductGroup, UUID> {
}

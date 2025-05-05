package com.onur.retail.api.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class CartResourceTest {

    @Inject
    EntityManager entityManager;

    @AfterEach
    @Transactional
    void cleanUp() {
        entityManager.createQuery("DELETE FROM OrderItem").executeUpdate();
        entityManager.createQuery("DELETE FROM Order").executeUpdate();
        entityManager.createQuery("DELETE FROM CartItem").executeUpdate();
        entityManager.createQuery("DELETE FROM Cart").executeUpdate();
        entityManager.createQuery("DELETE FROM Customer").executeUpdate();
        entityManager.createQuery("DELETE FROM User").executeUpdate();
        entityManager.createQuery("DELETE FROM ProductVariant").executeUpdate();
        entityManager.createQuery("DELETE FROM ProductGroup").executeUpdate();
    }

    @Test
    void shouldAddItemToCart() {
        // Step 1: Create customer
        String customerId = given()
                .contentType(ContentType.JSON)
                .body("""
                    {
                      "name": "Carty",
                      "surname": "McCartface",
                      "email": "carty@example.com",
                      "userType": "CUSTOMER",
                      "password": "cartpass123",
                      "phoneNumber": "+12345678901",
                      "address": "Cart Street"
                    }
                """)
                .post("/user/create")
                .then()
                .statusCode(201)
                .extract()
                .path("user.id");

        // Step 2: Create product group and get variantId
        String variantId = given()
                .contentType(ContentType.JSON)
                .body("""
                    {
                      "name": "Cart Product",
                      "description": "Test product for cart",
                      "variants": [
                        {
                          "imageUrl": "https://cdn.example.com/test.png",
                          "price": 89.99,
                          "originalPrice": 109.99,
                          "stock": 5,
                          "color": "Black",
                          "size": "L",
                          "isDefault": true
                        }
                      ]
                    }
                """)
                .post("/product-group/create")
                .then()
                .statusCode(201)
                .extract()
                .path("variants[0].id");

        // Step 3: Add to cart
        given()
                .contentType(ContentType.JSON)
                .body("""
                    {
                      "customerId": "%s",
                      "variationId": "%s",
                      "quantity": 2
                    }
                """.formatted(customerId, variantId))
                .post("/cart/add")
                .then()
                .statusCode(201)
                .body("items.size()", is(1))
                .body("items[0].quantity", equalTo(2))
                .body("items[0].productId", equalTo(variantId));
    }
}

package com.onur.retail.api.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;

@QuarkusTest
public class ProductGroupResourceTest {

    @Inject
    EntityManager entityManager;

    @AfterEach
    @Transactional
    void cleanUp() {
        entityManager.createQuery("DELETE FROM ProductVariant").executeUpdate();
        entityManager.createQuery("DELETE FROM ProductGroup").executeUpdate();
    }

    @Test
    void shouldCreateProductGroupWithVariants() {
        String requestBody = """
            {
              "name": "Test Product Group",
              "description": "Test description",
              "variants": [
                {
                  "imageUrl": "https://cdn.example.com/img/test.png",
                  "price": 100.00,
                  "originalPrice": 120.00,
                  "stock": 10,
                  "color": "Red",
                  "size": "M",
                  "isDefault": true
                }
              ]
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/product-groups")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("productName", equalTo("Test Product Group"))
                .body("variants.size()", is(1))
                .body("variants[0].isDefault", equalTo(true));
    }

    @Test
    void shouldFailWhenNameIsMissing() {
        String requestBody = """
        {
          "description": "Missing name field",
          "variants": [
            {
              "imageUrl": "https://cdn.example.com/img/test.png",
              "price": 100.00,
              "originalPrice": 120.00,
              "stock": 10,
              "color": "Red",
              "size": "M",
              "isDefault": true
            }
          ]
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/product-groups")
                .then()
                .statusCode(400)
                .body("violations", not(empty()));
    }

    @Test
    void shouldAllowMultipleDefaultsButOnlyKeepFirst() {
        String requestBody = """
        {
          "name": "Multi Default Product Group",
          "description": "Has multiple default variants",
          "variants": [
            {
              "imageUrl": "https://cdn.example.com/img/1.png",
              "price": 100.00,
              "originalPrice": 120.00,
              "stock": 10,
              "color": "Black",
              "size": "S",
              "isDefault": true
            },
            {
              "imageUrl": "https://cdn.example.com/img/2.png",
              "price": 105.00,
              "originalPrice": 125.00,
              "stock": 5,
              "color": "Blue",
              "size": "M",
              "isDefault": true
            }
          ]
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/product-groups")
                .then()
                .statusCode(201)
                .body("variants.size()", is(2))
                .body("variants[0].isDefault", equalTo(true))
                .body("variants[1].isDefault", equalTo(false));
    }

    @Test
    void shouldFailWhenVariantIsMissing() {
        String requestBody = """
        {
            "name": "No Variant Product Group",
          "description": "Missing variant",
          "variants": [
            {
            }
          ]
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/product-groups")
                .then()
                .statusCode(400)
                .body("violations", not(empty()));
    }

}

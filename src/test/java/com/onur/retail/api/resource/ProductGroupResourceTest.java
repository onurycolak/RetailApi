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
                .post("/product-group/create")
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
                .post("/product-group/create")
                .then()
                .statusCode(400)
                .body("violations", not(empty()));
    }

    @Test
    void shouldAllowMultipleDefaultsButOnlyKeepLast() {
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
                .post("/product-group/create")
                .then()
                .statusCode(201)
                .body("variants.size()", is(2))
                .body("variants.find { it.color == 'Blue' }.isDefault", equalTo(true))
                .body("variants.find { it.color == 'Black' }.isDefault", equalTo(false));
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
                .post("/product-group/create")
                .then()
                .statusCode(400)
                .body("violations", not(empty()));
    }

    @Test
    void shouldAddVariantsToExistingGroup() {
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
                  "color": "Black",
                  "size": "M",
                  "isDefault": true
                }
              ]
            }
            """;

        String groupId = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/product-group/create")
                .then()
                .statusCode(201)
                .extract()
                .path("id");
        System.out.println(groupId);
        String newVariantId = given()
            .contentType(ContentType.JSON)
            .body("""
                [
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
            """)
            .when()
            .post("/product-group/"+ groupId +"/add-variants")
            .then()
            .statusCode(201)
            .body("[0].id", notNullValue())
            .body("[0].isDefault", equalTo(true))
            .extract()
            .path("[0].id");

        given().when().get("/product-variant/" + newVariantId)
                .then().statusCode(200)
                .body("id", notNullValue())
                .body("color", equalTo("Red"))
                .body("isDefault", equalTo(true));

        String initialVariantId =
                given().when().get("/product-group/" + groupId)
                .then().statusCode(200)
                .body("variants.size()", is(2))
                .body("variants.find { it.color == 'Red' }.isDefault", equalTo(true))
                .body("variants.find { it.color == 'Black' }.isDefault", equalTo(false))
                        .extract()
                        .path("variants.find { it.color == 'Black' }.id");

        given().when().get("/product-variant/" + initialVariantId)
                .then().statusCode(200)
                .body("id", notNullValue())
                .body("color", equalTo("Black"))
                .body("isDefault", equalTo(false));
    }

    @Test
    void shouldRemoveGroup() {
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

        String groupId = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/product-group/create")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        String variantId = given().when().get("/product-group/" + groupId)
                        .then()
                        .statusCode(200)
                        .body("id", equalTo(groupId))
                .extract().path("variants[0].id");

        given().when().get("/product-variant/" + variantId)
                .then()
                .statusCode(200)
                .body("id", equalTo(variantId));

        given().when().delete("/product-group/" + groupId)
                .then()
                .statusCode(200);

        given().when().get("/product-group/" + groupId)
                .then()
                .statusCode(404);

        given().when().get("/product-variant/" + variantId)
                .then()
                .statusCode(404);
    }
}

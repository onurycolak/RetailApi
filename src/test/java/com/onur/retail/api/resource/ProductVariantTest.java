package com.onur.retail.api.resource;

import com.onur.retail.api.response.ProductGroupResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class ProductVariantTest {
    @Inject
    EntityManager entityManager;

    @AfterEach
    @Transactional
    void cleanUp() {
        entityManager.createQuery("DELETE FROM ProductVariant").executeUpdate();
        entityManager.createQuery("DELETE FROM ProductGroup").executeUpdate();
    }


    public record GroupAndVariants(String groupId, String firstVariantId, String secondVariantId) {}

    private GroupAndVariants createGroupAndVariants() {
        ProductGroupResponse group = given()
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
            },
            {
              "imageUrl": "https://cdn.example.com/test.png",
              "price": 89.99,
              "originalPrice": 109.99,
              "stock": 5,
              "color": "Red",
              "size": "M",
              "isDefault": false
            }
          ]
        }
        """)
                .post("/product-group/create")
                .then()
                .statusCode(201).body("variants.size()", is(2))
                .extract()
                .as(ProductGroupResponse.class);

        String groupId = group.getId().toString();
        String firstVariantId = group.getVariants().get(0).getId().toString();
        String secondVariantId = group.getVariants().get(1).getId().toString();

        return new GroupAndVariants(groupId, firstVariantId, secondVariantId);
    }

    @Test
    void shouldGetVariant() {
        GroupAndVariants data = createGroupAndVariants();

        given().get("/product-variant/" + data.firstVariantId)
                .then()
                .statusCode(200).body("id", equalTo(data.firstVariantId));
    }

    @Test
    void shouldRemoveVariant() {
        GroupAndVariants data = createGroupAndVariants();

        given().get("/product-variant/" + data.secondVariantId)
                .then()
                .statusCode(200).body("id", equalTo(data.secondVariantId));

        given().delete("/product-variant/" + data.secondVariantId)
                .then()
                .statusCode(200);

        System.out.println(data.secondVariantId);
        given().get("/product-variant/" + data.secondVariantId)
                .then()
                .statusCode(404);

        given().get("/product-group/" + data.groupId)
                .then()
                .statusCode(200)
                .body("variants.size()", is(1))
                .body("variants.find { it.color == 'Black' }.isDefault", equalTo(true));
    }

    @Test
    void shouldNotRemoveTheLastVariant () {
        GroupAndVariants data = createGroupAndVariants();

        given().get("/product-variant/" + data.secondVariantId)
                .then()
                .statusCode(200).body("id", equalTo(data.secondVariantId));

        given().delete("/product-variant/" + data.secondVariantId)
                .then()
                .statusCode(200);

        given().get("/product-variant/" + data.secondVariantId)
                .then()
                .statusCode(404);

        given().get("/product-group/" + data.groupId)
                .then()
                .statusCode(200)
                .body("variants.size()", is(1))
                .body("variants.find { it.color == 'Black' }.isDefault", equalTo(true));

        given().delete("/product-variant/" + data.firstVariantId)
                .then()
                .statusCode(400);

        given().get("/product-group/" + data.groupId)
                .then()
                .statusCode(200)
                .body("variants.size()", is(1))
                .body("variants.find { it.color == 'Black' }.isDefault", equalTo(true));
    }

    @Test
    void shouldUpdateDefaultOnRemove() {
        GroupAndVariants data = createGroupAndVariants();

        given().delete("/product-variant/" + data.firstVariantId)
                .then()
                .statusCode(200);

        given().get("/product-variant/" + data.firstVariantId)
                .then()
                .statusCode(404);

        given().get("/product-group/" + data.groupId)
                .then()
                .statusCode(200)
                .body("variants.size()", is(1))
                .body("variants.find { it.color == 'Red' }.isDefault", equalTo(true));

        given().get("/product-variant/" + data.secondVariantId)
                .then()
                .statusCode(200).body("isDefault", equalTo(true));
    }
}

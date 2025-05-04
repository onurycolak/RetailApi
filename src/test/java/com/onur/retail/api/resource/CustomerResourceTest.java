package com.onur.retail.api.resource;

import com.onur.retail.domain.UserType;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class CustomerResourceTest {
    @Inject
    EntityManager entityManager;

    @AfterEach
    @Transactional
    void cleanUp() {
        entityManager.createQuery("DELETE FROM OrderItem").executeUpdate();
        entityManager.createQuery("DELETE FROM Order").executeUpdate();
        entityManager.createQuery("DELETE FROM CartItem").executeUpdate();
        entityManager.createQuery("DELETE FROM Cart").executeUpdate();
        entityManager.createQuery("DELETE FROM User").executeUpdate();
        entityManager.createQuery("DELETE FROM Customer").executeUpdate();
    }

    private String validCustomerJson(String email, String phoneNumber) {
        String formattedPhone = (phoneNumber == null) ? "null" : "\"" + phoneNumber + "\"";

        return """
        {
          "name": "John",
          "surname": "Doe",
          "email": "%s",
          "userType": "CUSTOMER",
          "password": "securePassword123",
          "phoneNumber": %s,
          "address": "123 Main Street"
        }
    """.formatted(email, formattedPhone);
    }


    @Test
    void shouldCreateCustomer() {
        String requestBody = validCustomerJson("email@email.com", "+12345678");

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/create-customer")
                .then()
                .statusCode(201)
                .body("user.id", notNullValue())
                .body("user.name", equalTo("John"))
                .body("user.userType", is(UserType.CUSTOMER.toString()))
                .body("password", nullValue());
    }

    @Test
    void shouldFailWhenPasswordMissing() {
        String requestBody = """
            {
              "name": "John",
              "surname": "Doe",
              "email": "john.doe@example.com",
              "userType": "CUSTOMER",
              "phoneNumber": "+1234567890",
              "address": "123 Main Street, Cityville"
            }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/create-customer")
                .then()
                .statusCode(400)
                .body("violations", not(empty()));
    }

    @Test
    void shouldFailIfEmailExits() {
        String existingUser = validCustomerJson("email@email.com", "+12345678");
        String newUser = validCustomerJson("email@email.com", null);

        given()
                .contentType(ContentType.JSON)
                .body(existingUser)
                .when()
                .post("/create-customer")
                .then()
                .statusCode(201)
                .body("user.email", equalTo("email@email.com"));

        given()
                .contentType(ContentType.JSON)
                .body(newUser)
                .when()
                .post("/create-customer")
                .then()
                .statusCode(400)
                .body("message", containsString("already exists"));
    }

    @Test
    void shouldFailIfPhoneExits() {
        String existingUser = validCustomerJson("john@email.com", "+12345678");
        String newUser = validCustomerJson("alice@email.com", "+12345678");

        given()
                .contentType(ContentType.JSON)
                .body(existingUser)
                .when()
                .post("/create-customer")
                .then()
                .statusCode(201)
                .body("phoneNumber", equalTo("+12345678"));

        given()
                .contentType(ContentType.JSON)
                .body(newUser)
                .when()
                .post("/create-customer")
                .then()
                .statusCode(400)
                .body("message", containsString("already exists"));
    }
}

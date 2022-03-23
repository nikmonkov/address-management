package com.nikmonkov.address.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class AddressComponentResourceTest {

    @Test
    void get() {
        given()
                .when().get("/api/v1/address/1")
                .then().statusCode(200);
    }
}
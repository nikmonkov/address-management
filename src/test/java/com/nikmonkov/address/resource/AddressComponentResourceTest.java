package com.nikmonkov.address.resource;

import com.nikmonkov.address.database.entity.AddressComponentEntity;
import com.nikmonkov.address.database.repo.AddressComponentRepository;
import com.nikmonkov.address.model.AddressComponent;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static io.restassured.RestAssured.given;

@QuarkusTest
class AddressComponentResourceTest {

    @InjectMock
    AddressComponentRepository addressComponentRepository;

    @BeforeEach
    void setUp() {
        Mockito.when(addressComponentRepository.findById(Mockito.any()))
                .thenReturn(new AddressComponentEntity("1", "test", null));
    }

    @Test
    void get() {
        given()
                .when().get("/api/v1/address/1")
                .then().statusCode(200)
                .body("$", Matchers.hasKey("id"));
    }

    @Test
    void successfulCreate() {
        given()
                .contentType(ContentType.JSON)
                .body(new AddressComponent(UUID.randomUUID().toString(), "test", null))
                .when().post("/api/v1/address")
                .then().statusCode(200);
    }

    @Test
    void cantCreateWithoutName() {
        given()
                .contentType(ContentType.JSON)
                .body(new AddressComponent())
                .when().post("/api/v1/address")
                .then().statusCode(400);
    }

    @Test
    void searchByName() {
    }

    @Test
    void getByParent() {
    }
}
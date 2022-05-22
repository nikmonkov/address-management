package com.nikmonkov.address.resource;

import com.nikmonkov.address.database.entity.AddressComponentEntity;
import com.nikmonkov.address.database.repo.AddressComponentRepository;
import com.nikmonkov.address.model.AddressComponent;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

@QuarkusTest
class AddressComponentResourceTest {

    @InjectMock
    AddressComponentRepository addressComponentRepository;

    @BeforeEach
    void setUp() {
        Mockito.when(addressComponentRepository.findById(Mockito.any()))
                .thenReturn(new AddressComponentEntity("1", "test", null));

        Mockito.when(addressComponentRepository.findByName(Mockito.any()))
                .thenReturn(List.of(new AddressComponentEntity("1", "test", null)));

        Mockito.when(addressComponentRepository.findByParentId(Mockito.any()))
                .thenReturn(List.of(new AddressComponentEntity("1", "test", null)));
    }

    @Test
    void getById() {
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
        given()
                .when().get("/api/v1/address/search?name=test")
                .then().statusCode(200)
                .body("size()", is(1));
    }

    @Test
    void getByParent() {
        given()
                .when().get("/api/v1/address?parent_id=1")
                .then().statusCode(200)
                .body("size()", is(1));
    }
}
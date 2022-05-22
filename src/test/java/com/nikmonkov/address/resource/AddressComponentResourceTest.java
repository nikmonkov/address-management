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
        Mockito.when(addressComponentRepository.findById("1"))
                .thenReturn(new AddressComponentEntity("1", "test", null));

        Mockito.when(addressComponentRepository.findByName("test"))
                .thenReturn(List.of(new AddressComponentEntity("1", "test", null)));

        Mockito.when(addressComponentRepository.findByParentId("parent"))
                .thenReturn(List.of(new AddressComponentEntity("1", "test", null)));

        Mockito.when(addressComponentRepository.findById("new"))
                .thenReturn(new AddressComponentEntity("new", "test", null));
    }

    @Test
    void getById() {
        given()
                .when().get("/api/v1/address/1")
                .then().statusCode(200)
                .body("$", Matchers.hasKey("id"));
    }

    @Test
    void getByIdNotFound() {
        given()
                .when().get("/api/v1/address/2")
                .then().statusCode(404);
    }

    @Test
    void successfulCreate() {
        given()
                .contentType(ContentType.JSON)
                .body(new AddressComponent("new", "test", null))
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
    void searchByNameIsEmpty() {
        given()
                .when().get("/api/v1/address/search?name=test2")
                .then().statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void getByParent() {
        given()
                .when().get("/api/v1/address?parent_id=parent")
                .then().statusCode(200)
                .body("size()", is(1));
    }

    @Test
    void getByParentIsEmpty() {
        given()
                .when().get("/api/v1/address?parent_id=2")
                .then().statusCode(200)
                .body("size()", is(0));
    }
}
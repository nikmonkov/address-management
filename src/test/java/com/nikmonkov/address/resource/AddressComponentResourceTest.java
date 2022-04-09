package com.nikmonkov.address.resource;

import com.nikmonkov.address.database.entity.AddressComponentEntity;
import com.nikmonkov.address.database.repo.AddressComponentRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;

@QuarkusTest
class AddressComponentResourceTest {

    @InjectMock
    AddressComponentRepository addressComponentRepository;

    @BeforeEach
    void setUp() {
        Mockito.when(addressComponentRepository.findById(Mockito.any())).thenReturn(new AddressComponentEntity());
    }

    @Test
    void get() {
        given()
                .when().get("/api/v1/address/1")
                .then().statusCode(200);
    }

    @Test
    void create() {
    }

    @Test
    void searchByName() {
    }

    @Test
    void getByParent() {
    }
}
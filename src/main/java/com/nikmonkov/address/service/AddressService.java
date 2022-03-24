package com.nikmonkov.address.service;

import com.nikmonkov.address.model.AddressComponent;

import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class AddressService {

    List<AddressComponent> data = Arrays.asList(new AddressComponent("1", "one", null), new AddressComponent("2", "two", null));

    public AddressComponent getById(String id) {
        return new AddressComponent(id, "test", null);
    }

    public List<AddressComponent> searchAddress(String name) {
        return data;
    }
}

package com.nikmonkov.address.service;

import com.nikmonkov.address.database.entity.AddressComponentEntity;
import com.nikmonkov.address.database.repo.AddressComponentRepository;
import com.nikmonkov.address.model.AddressComponent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class AddressService {

    @Inject
    AddressComponentRepository addressComponentRepository;

    List<AddressComponent> data = Arrays.asList(new AddressComponent("1", "one", null), new AddressComponent("2", "two", null));

    public AddressComponent getById(String id) {
        return new AddressComponent(id, "test", null);
    }

    public List<AddressComponent> searchAddress(String name) {
        return data;
    }

    @Transactional
    public AddressComponent create(AddressComponent addressComponent) {
        AddressComponentEntity entity = new AddressComponentEntity();
        entity.setName(addressComponent.getName());
        addressComponentRepository.persist(entity);
        addressComponent.setId(entity.getId());
        return addressComponent;
    }
}

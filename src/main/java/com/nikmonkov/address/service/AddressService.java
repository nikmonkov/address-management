package com.nikmonkov.address.service;

import com.nikmonkov.address.database.entity.AddressComponentEntity;
import com.nikmonkov.address.database.repo.AddressComponentRepository;
import com.nikmonkov.address.model.AddressComponent;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AddressService {

    @Inject
    AddressComponentRepository addressComponentRepository;

    @Timed(name = "getById")
    @Counted(name = "getByIdCount")
    public AddressComponent getById(String id) {
        AddressComponentEntity addressComponentEntity = addressComponentRepository.findById(id);
        return new AddressComponent(addressComponentEntity.getId(), addressComponentEntity.getName(), null);
    }

    public List<AddressComponent> searchAddress(String name) {
        List<AddressComponentEntity> data = addressComponentRepository.findByName(name);
        return data.stream().map(item -> new AddressComponent(item.getId(), item.getName(), null)).collect(Collectors.toList());
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

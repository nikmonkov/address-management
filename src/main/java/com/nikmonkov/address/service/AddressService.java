package com.nikmonkov.address.service;

import com.nikmonkov.address.database.entity.AddressComponentEntity;
import com.nikmonkov.address.database.repo.AddressComponentRepository;
import com.nikmonkov.address.model.AddressComponent;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
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
        return convertToDto(addressComponentEntity);
    }

    private AddressComponent convertToDto(AddressComponentEntity addressComponentEntity) {
        AddressComponent parent = null;
        if (addressComponentEntity.getParent() != null) {
            parent = convertToDto(addressComponentEntity.getParent());
        }
        return new AddressComponent(addressComponentEntity.getId(), addressComponentEntity.getName(), parent);
    }

    public List<AddressComponent> searchAddress(String name) {
        List<AddressComponentEntity> data = addressComponentRepository.findByName(name);
        return data.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public AddressComponent create(AddressComponent addressComponent) {
        AddressComponentEntity entity = convertToEntity(addressComponent);
        addressComponentRepository.persist(entity);
        AddressComponentEntity byId = addressComponentRepository.findById(entity.getId());
        return convertToDto(byId);
    }

    private void fillFields(AddressComponent addressComponent, AddressComponentEntity entity) {
        addressComponent.setId(entity.getId());
        addressComponent.setName(entity.getName());
        if (entity.getParent() != null) {
            fillFields(addressComponent.getParent(), entity.getParent());
        }
    }

    private AddressComponentEntity convertToEntity(AddressComponent addressComponent) {
        AddressComponentEntity entity;
        if (addressComponent.getId() != null) {
            entity = addressComponentRepository.findById(addressComponent.getId());
        } else {
            entity = new AddressComponentEntity();
            entity.setId(addressComponent.getId());
            entity.setName(addressComponent.getName());
        }
        if (addressComponent.getParent() != null) {
            entity.setParent(convertToEntity(addressComponent.getParent()));
        }
        return entity;
    }
}

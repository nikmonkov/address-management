package com.nikmonkov.address.service;

import com.nikmonkov.address.database.entity.AddressComponentEntity;
import com.nikmonkov.address.database.repo.AddressComponentRepository;
import com.nikmonkov.address.model.AddressComponent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AddressService {

    @Inject
    AddressComponentRepository addressComponentRepository;

    public AddressComponent getById(String id) {
        AddressComponentEntity addressComponentEntity = addressComponentRepository.findById(id);
        if (addressComponentEntity == null) {
            return null;
        }
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

    private AddressComponentEntity convertToEntity(AddressComponent addressComponent) {
        AddressComponentEntity entity;
        if (addressComponent.getId() != null) {
            entity = addressComponentRepository.findById(addressComponent.getId());
            if (entity == null) {
                throw new RuntimeException("address component with id = " + addressComponent.getId()
                        + " doesn't exist");
            }
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

    public List<AddressComponent> findByParentId(String parentId) {
        List<AddressComponentEntity> entities = addressComponentRepository.findByParentId(parentId);
        List<AddressComponent> addressComponents = entities.stream().map(this::convertToDto)
                .collect(Collectors.toList());
        return addressComponents;
    }
}

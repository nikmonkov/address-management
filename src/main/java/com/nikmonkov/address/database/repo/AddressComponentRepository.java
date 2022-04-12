package com.nikmonkov.address.database.repo;


import com.nikmonkov.address.database.entity.AddressComponentEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class AddressComponentRepository implements PanacheRepositoryBase<AddressComponentEntity, String> {

    public List<AddressComponentEntity> findByName(String name) {
        return find("name", name).list();
    }
}

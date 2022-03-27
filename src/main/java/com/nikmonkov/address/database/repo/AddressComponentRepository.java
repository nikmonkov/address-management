package com.nikmonkov.address.database.repo;


import com.nikmonkov.address.database.entity.AddressComponentEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AddressComponentRepository implements PanacheRepositoryBase<AddressComponentEntity, String> {
}

package com.nikmonkov.address.resource;

import com.nikmonkov.address.model.AddressComponent;
import com.nikmonkov.address.service.AddressService;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

@Path("/api/v1/address")
@Produces(MediaType.APPLICATION_JSON)
public class AddressComponentResource {

    @Inject
    AddressService addressService;

    @GET
    @Path("/{id}")
    public AddressComponent get(@PathParam("id") String id) {

        return addressService.getById(id);
    }

    @GET
    @Path("/search")
    public List<AddressComponent> searchByName(@QueryParam("name") String name) {
        if (StringUtils.isEmpty(name)) {
            throw new BadRequestException();
        }
        return addressService.searchAddress(name);
    }

    @GET
    public List<AddressComponent> getByParent(@QueryParam("parent_id") String parentId) {
        if (StringUtils.isEmpty(parentId)) {
            throw new BadRequestException();
        }
        return Collections.emptyList();
    }

}

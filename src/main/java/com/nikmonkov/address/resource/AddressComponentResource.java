package com.nikmonkov.address.resource;

import com.nikmonkov.address.model.AddressComponent;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/v1/address")
@Produces(MediaType.APPLICATION_JSON)
public class AddressComponentResource {

    @GET
    @Path("/{id}")
    public AddressComponent get(@PathParam("id") String id) {

        return new AddressComponent();
    }
}

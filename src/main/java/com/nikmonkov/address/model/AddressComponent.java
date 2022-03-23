package com.nikmonkov.address.model;

import lombok.Data;

@Data
public class AddressComponent {

    private String id;
    private String name;
    private AddressComponent parent;

}

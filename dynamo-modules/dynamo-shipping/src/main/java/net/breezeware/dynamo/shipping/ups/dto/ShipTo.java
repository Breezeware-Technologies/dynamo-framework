package net.breezeware.dynamo.shipping.ups.dto;

import lombok.Data;

@Data
public class ShipTo {

    private String name;

    private Phone phone;

    private Address address;

}

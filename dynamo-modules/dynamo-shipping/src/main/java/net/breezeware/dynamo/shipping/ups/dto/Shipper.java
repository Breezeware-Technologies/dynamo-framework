package net.breezeware.dynamo.shipping.ups.dto;

import lombok.Data;

@Data
public class Shipper {

    private String name;

    private String shipperNumber;

    private Phone phone;

    private Address address;

}

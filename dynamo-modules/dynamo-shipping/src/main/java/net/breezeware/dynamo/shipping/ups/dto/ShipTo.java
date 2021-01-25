package net.breezeware.dynamo.shipping.ups.dto;

import lombok.Data;

/**
 * Dto Set a Shipto(destination) Details
 */
@Data
public class ShipTo {

    private String name;

    private Phone phone;

    private Address address;

}

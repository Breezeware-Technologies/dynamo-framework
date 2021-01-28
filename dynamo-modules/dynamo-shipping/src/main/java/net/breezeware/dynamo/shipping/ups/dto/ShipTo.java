package net.breezeware.dynamo.shipping.ups.dto;

import lombok.Data;

/**
 * The DTO to Set the {@link ShipTo} in the Shipment.
 */
@Data
public class ShipTo {

    private String name;

    private Phone phone;

    private Address address;

}

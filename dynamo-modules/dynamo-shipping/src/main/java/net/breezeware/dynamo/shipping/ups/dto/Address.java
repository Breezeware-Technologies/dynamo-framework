package net.breezeware.dynamo.shipping.ups.dto;

import lombok.Data;

/**
 * The DTO to set Address in the {@link Shipper} and {@link ShipTo}.
 */
@Data
public class Address {

    private String addressLine;

    private String city;
    /**
     * State Province code(eg:GA)
     */
    private String stateProvinceCode;
    /**
     * Postal Code for the given State
     */
    private String postalCode;
    /**
     * countryCode (eg:US)
     */
    private String countryCode;

}

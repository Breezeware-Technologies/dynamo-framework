package net.breezeware.dynamo.shipping.ups.dto;

import lombok.Data;

/**
 * Address Dto for set the all address oriented details for the Shipment
 * requests.
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

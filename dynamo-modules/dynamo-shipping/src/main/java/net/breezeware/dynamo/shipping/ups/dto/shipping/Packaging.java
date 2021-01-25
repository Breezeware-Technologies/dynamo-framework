package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * Shipping Request Dto to set the Packaging Details for the single Shipment
 * Request.
 */
@Data
public class Packaging {
    /**
     * code vaule of the packaing for the specific request(eg:"02",02=Customer
     * Supplied Package) Maximum String Length is 2.
     */
    private String code;

}

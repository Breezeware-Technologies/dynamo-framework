package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * Shipping Request Dto to set the Service Details for the single Shipment
 * Request
 */
@Data
public class Service {
    /**
     * code vaule of the current using shipment Service (eg:03 = Ground)
     */
    private String code;

    private String description;

}

package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * DTO to set the Service in the {@link Shipment}
 */
@Data
public class Service {
    /**
     * code vaule of the current using shipment Service (eg:03 = Ground)
     */
    private String code;

    private String description;

}

package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * The wrapper class used for Parsing {@link ShipmentResponse} properties
 */
@Data
public class ShipmentResponseWrapper {
    private ShipmentResponse shipmentResponse;
}

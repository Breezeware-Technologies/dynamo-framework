package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * Shipping Request Dto to set the ShipmentRequest for the single Shipment
 * Request. Shippimg Requests intal(parent) Dto.
 */
@Data
public class ShipmentRequest {

    private Shipment shipment;

}

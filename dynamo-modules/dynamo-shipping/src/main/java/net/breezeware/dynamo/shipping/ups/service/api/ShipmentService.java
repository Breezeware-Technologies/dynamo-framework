package net.breezeware.dynamo.shipping.ups.service.api;

import net.breezeware.dynamo.shipping.ups.dto.ShipmentRequest;

public interface ShipmentService {

    String createShippingLabel(ShipmentRequest shipmentRequest);

    // Shipment populatShipmentDto();

    // ShipmentRequest populateShippingLabelCreation(Shipment shipment);

}

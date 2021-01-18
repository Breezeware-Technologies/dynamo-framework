package net.breezeware.dynamo.shipping.service.api;

import net.breezeware.dynamo.shipping.dto.ShipmentRequest;

public interface ShipmentService {

    String createShippingLabel(ShipmentRequest shipmentRequest);

    // Shipment populatShipmentDto();

    // ShipmentRequest populateShippingLabelCreation(Shipment shipment);

}

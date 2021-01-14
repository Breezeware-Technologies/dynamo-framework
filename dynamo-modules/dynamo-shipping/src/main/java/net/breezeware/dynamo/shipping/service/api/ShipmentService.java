package net.breezeware.dynamo.shipping.service.api;

import net.breezeware.dynamo.shipping.dto.Shipment;
import net.breezeware.dynamo.shipping.dto.ShipmentRequest;

public interface ShipmentService {

    String CreateShippingLabel(ShipmentRequest shipmentRequest);

    Shipment populatShipmentDto();

    ShipmentRequest populateShippingLabelCreation(Shipment shipment);

}

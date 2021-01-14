package net.breezeware.dynamo.shipping.service.api;

import net.breezeware.dynamo.shipping.dto.ShipmentRequest;

public interface ShipmentService {

    String CreateShippingLabel(ShipmentRequest shipmentRequest);

    ShipmentRequest populateShippingLabelCreation();

}

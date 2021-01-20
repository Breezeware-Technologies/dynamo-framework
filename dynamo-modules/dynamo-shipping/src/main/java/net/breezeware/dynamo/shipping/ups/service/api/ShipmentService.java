package net.breezeware.dynamo.shipping.ups.service.api;

import net.breezeware.dynamo.shipping.ups.dto.shipping.ShipmentRequest;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ShipmentResponse;

public interface ShipmentService {

    ShipmentResponse createShippingLabel(ShipmentRequest shipmentRequest);

}

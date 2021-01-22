package net.breezeware.dynamo.shipping.ups.service.api;

import net.breezeware.dynamo.shipping.ups.dto.pickup.PickupCreationRequest;
import net.breezeware.dynamo.shipping.ups.dto.pickup.PickupCreationResponse;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ShipmentRequest;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ShipmentResponse;
import net.breezeware.dynamo.shipping.ups.dto.tracking.TrackResponse;

public interface ShipmentService {

    ShipmentResponse createShippingLabel(ShipmentRequest shipmentRequest);

    PickupCreationResponse pickupCreation(PickupCreationRequest pickupCreationRequest);

    TrackResponse getTrackingDetails(String trackingNumber);

}

package net.breezeware.dynamo.shipping.ups.service.api;

import net.breezeware.dynamo.shipping.ups.dto.pickup.PickupCreationRequest;
import net.breezeware.dynamo.shipping.ups.dto.pickup.PickupCreationResponse;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ShipmentRequest;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ShipmentResponse;
import net.breezeware.dynamo.shipping.ups.dto.tracking.TrackResponse;

/**
 * ShipmentService Uses UPS API's for test driven development with out
 * modelling.
 */
public interface ShipmentService {
    /**
     * createShippingLabel for ShipmentRequest using UPS API.
     * @param shipmentRequest shipment Request dto
     * @return shipmentResponse dto
     */
    ShipmentResponse createShippingLabel(ShipmentRequest shipmentRequest);

    /**
     * create pickup request for PickupCreationRequest Using UPS API
     * @param pickupCreationRequest
     * @return PickupCreationResponse dto
     */
    PickupCreationResponse pickupCreation(PickupCreationRequest pickupCreationRequest);

    /**
     * Get Track Details for the given Trackingnumber
     * @param trackingNumber
     * @return TrackResponse dto
     */
    TrackResponse getTrackingDetails(String trackingNumber);

}

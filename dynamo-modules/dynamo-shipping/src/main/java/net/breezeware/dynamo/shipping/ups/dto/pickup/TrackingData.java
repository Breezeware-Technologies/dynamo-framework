package net.breezeware.dynamo.shipping.ups.dto.pickup;

import lombok.Data;

/**
 * DTO to set the TrackingData in the {@link PickupCreationRequest}.
 */
@Data
public class TrackingData {

    private String trackingNumber;

}

package net.breezeware.dynamo.shipping.ups.dto.pickup;

import lombok.Data;

/**
 * Set the Track number in the Current Pickup Request.(optional)
 */
@Data
public class TrackingData {

    private String trackingNumber;

}

package net.breezeware.dynamo.shipping.ups.dto.pickup;

import lombok.Data;

/**
 * DTO to set the PickupDateInfo in the {@link PickupCreationRequest}.
 */
@Data
public class PickupDateInfo {
    /**
     * Pickup location's local close time. (Format: HHmm, Hour: 0-23 Minute: 0-59)
     */
    private String closeTime;
    /**
     * Pickup location's local ready time. (Format: HHmm ,Hour: 0-23 Minute: 0-59)
     */
    private String readyTime;
    /**
     * local pickup date of the location.(Format: yyyyMMdd, yyyy = Year Applicable,
     * MM = 01– 12, dd = 01–31)
     */
    private String pickupDate;

}

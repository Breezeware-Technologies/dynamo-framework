package net.breezeware.dynamo.shipping.ups.dto.pickup;

import lombok.Data;

/**
 * Request Dto for set's the PickupDateInfo Details
 */
@Data
public class PickupDateInfo {
    /**
     * Pickup location's local close time. (Format: HHmm, Hour: 0-23 Minute: 0-59)
     */
    private String CloseTime;
    /**
     * Pickup location's local ready time. (Format: HHmm ,Hour: 0-23 Minute: 0-59)
     */
    private String ReadyTime;
    /**
     * Local pickup date of the location.(Format: yyyyMMdd, yyyy = Year Applicable,
     * MM = 01– 12, dd = 01–31)
     */
    private String PickupDate;

}

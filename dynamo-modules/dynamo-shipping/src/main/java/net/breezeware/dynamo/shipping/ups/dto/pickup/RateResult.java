package net.breezeware.dynamo.shipping.ups.dto.pickup;

import java.util.List;

import lombok.Data;

/**
 * Response dto for the persits the Rate Results Whether the
 * ratePickupIndicator=Y
 */
@Data
public class RateResult {
    /**
     * Indicates this pickup is rated as same-day or future-day pickup.(SD =
     * Same-day Pickup FD = Future-day Pickup)
     */
    private String rateType;
    /**
     * currency codes for the pickup charge.(eg:USD)
     */
    private String currencyCode;
    /**
     * Detailed charges
     */
    private List<ChargeDetail> ChargeDetails;
    /**
     * grand Total For the Pickup
     */
    private String grandTotalOfAllCharge;

}

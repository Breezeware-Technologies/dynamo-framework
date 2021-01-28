package net.breezeware.dynamo.shipping.ups.dto.pickup;

import java.util.List;

import lombok.Data;

/**
 * DTO to persist all the RateResult for {@link PickupCreationResponse} Whether
 * the ratePickupIndicator property is true in {@link PickupCreationRequest}.
 */
@Data
public class RateResult {
    /**
     * indicates this pickup is rated as same-day or future-day pickup.(SD =
     * Same-day pickup FD = Future-day pickup)
     */
    private String rateType;
    /**
     * currency codes for the pickup charge.(eg:USD)
     */
    private String currencyCode;
    /**
     * detailed charges
     */
    private List<ChargeDetail> chargeDetail;
    /**
     * grand Total For the pickup
     */
    private String grandTotalOfAllCharge;

}

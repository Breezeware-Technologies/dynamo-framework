package net.breezeware.dynamo.shipping.ups.dto.pickup;

import lombok.Data;

/**
 * DTO to persist the ChargeDetail for {@link RateResult}.
 */
@Data
public class ChargeDetail {
    /**
     * Charge Code Refers the general charge type (eg:A = ACCESSORIAL TYPE,B = BASE
     * CHARGE TYPE,S = SURCHARGE TYPE)
     */
    private String chargeCode;

    private String chargeDescription;

    private String chargeAmount;

    private String taxAmount;
}

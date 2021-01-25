package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * Response Dto for Persits TotalCharges Details.
 */
@Data
public class TotalCharges {
    /**
     * currencyCode value of the charges for the given Request(eg:USD)
     */
    private String currencyCode;

    private String monetaryvalue;

}

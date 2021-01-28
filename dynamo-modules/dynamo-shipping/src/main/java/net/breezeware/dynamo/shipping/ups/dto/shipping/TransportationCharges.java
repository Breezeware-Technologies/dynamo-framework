package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * The DTO to Persists the TotalCharges for {@link ShipmentCharges}.
 */
@Data
public class TransportationCharges {
    /**
     * currencyCode value of the charges for the given Request(eg:USD)
     */
    private String currencyCode;

    private String monetaryValue;

}

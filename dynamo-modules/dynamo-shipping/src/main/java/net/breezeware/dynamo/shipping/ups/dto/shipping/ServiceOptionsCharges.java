package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * DTO to persist the ServiceOptionsCharges for {@link ShipmentCharges}.
 */
@Data
public class ServiceOptionsCharges {
    /**
     * currencyCode value of the charges for the given Request(eg:USD)
     */
    private String currencyCode;

    private String monetaryValue;

}

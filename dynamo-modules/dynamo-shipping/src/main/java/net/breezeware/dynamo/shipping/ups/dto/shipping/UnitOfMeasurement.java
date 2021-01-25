package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * Shipping Request Dto to set the UnitOfMeasurement Details for the single
 * Shipment Request
 */
@Data
public class UnitOfMeasurement {
    /**
     * code value of this String refers the Unit Measurement of the Given
     * package(eg:LBS,KG)
     */
    private String code;

    private String description;

}

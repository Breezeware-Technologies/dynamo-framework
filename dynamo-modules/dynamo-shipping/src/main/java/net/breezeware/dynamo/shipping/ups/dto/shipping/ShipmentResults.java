package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * Response Dto for Persits ShipmentResults.
 */
@Data
public class ShipmentResults {

    private ShipmentCharges shipmentCharges;

    private BillingWeight bllingWeight;

    private String shipmentIdentificationNumber;

    private PackageResults packageResults;

}

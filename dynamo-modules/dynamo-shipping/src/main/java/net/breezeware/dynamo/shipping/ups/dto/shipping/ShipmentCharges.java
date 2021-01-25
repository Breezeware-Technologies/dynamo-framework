package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * Response Dto to persits ShipmentCharges for the given Request.
 */
@Data
public class ShipmentCharges {

    private TransportationCharges transportationCharges;
    private ServiceOptionsCharges serviceOptionsCharges;
    private TotalCharges totalCharges;

}

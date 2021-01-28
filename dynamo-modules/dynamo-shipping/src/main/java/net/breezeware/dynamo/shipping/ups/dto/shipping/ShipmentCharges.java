package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * DTO to persists the ShipmentCharges for {@link ShipmentResults}.
 */
@Data
public class ShipmentCharges {

    private TransportationCharges transportationCharges;
    private ServiceOptionsCharges serviceOptionsCharges;
    private TotalCharges totalCharges;

}

package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

@Data
public class ShipmentCharges {

    private TransportationCharges transportationCharges;
    private ServiceOptionsCharges serviceOptionsCharges;
    private TotalCharges totalCharges;

}

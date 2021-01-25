package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * Shipping Request Dto to set the ShipmentCharge Details for the single
 * Shipment Request.
 */
@Data
public class ShipmentCharge {
    /**
     * Type value of the shipmentCharge is specific Request(eg:01 = Transportation)
     */
    public String type;
    private BillShipper billShipper;

}

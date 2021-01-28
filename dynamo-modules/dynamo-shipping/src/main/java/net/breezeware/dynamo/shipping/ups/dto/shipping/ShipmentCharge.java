package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * The Dto to set ShipmentCharge Details in the {@link PaymentInformation}.
 */
@Data
public class ShipmentCharge {
    /**
     * Type value of the shipmentCharge is specific Request(eg:01 = Transportation)
     */
    public String type;
    private BillShipper billShipper;

}

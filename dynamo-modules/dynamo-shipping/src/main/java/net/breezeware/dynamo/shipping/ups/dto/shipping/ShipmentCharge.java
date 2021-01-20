package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

@Data
public class ShipmentCharge {

    public String type;
    private BillShipper billShipper;

}

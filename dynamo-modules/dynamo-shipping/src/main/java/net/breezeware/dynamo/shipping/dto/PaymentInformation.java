package net.breezeware.dynamo.shipping.dto;

import lombok.Data;

@Data
public class PaymentInformation {

    private ShipmentCharge shipmentCharge;

    private BillShipper billShipper;

}

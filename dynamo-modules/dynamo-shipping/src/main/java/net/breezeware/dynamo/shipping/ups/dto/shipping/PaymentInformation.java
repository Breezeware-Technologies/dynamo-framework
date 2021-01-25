package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * Shipping Request Dto to set the PaymentInformation Details for the single Shipment
 * Request
 * 
 *
 */
@Data
public class PaymentInformation {

    private ShipmentCharge shipmentCharge;

}

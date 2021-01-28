package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * DTO to set the PaymentInformation in the {@link Shipment}.
 */
@Data
public class PaymentInformation {

    private ShipmentCharge shipmentCharge;

}

package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * The DTO to persist PackageResults for {@link ShipmentResults}.
 */
@Data
public class PackageResults {

    private String trackingNumber;

    private ServiceOptionsCharges serviceOptionsCharges;

    private ShippingLabel shippingLabel;

}

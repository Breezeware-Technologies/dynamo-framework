package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

@Data
public class PackageResults {

    private String TrackingNumber;

    private ServiceOptionsCharges serviceOptionsCharges;

    private ShippingLabel ShippingLabel;

}

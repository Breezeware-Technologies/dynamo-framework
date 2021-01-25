package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * Response Dto to persits packageDetails for the given Request.
 */
@Data
public class PackageResults {

    private String TrackingNumber;

    private ServiceOptionsCharges serviceOptionsCharges;

    private ShippingLabel ShippingLabel;

}

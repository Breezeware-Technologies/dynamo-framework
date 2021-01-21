package net.breezeware.dynamo.shipping.ups.dto.pickup;

import lombok.Data;
import net.breezeware.dynamo.shipping.ups.dto.Shipper;

@Data
public class PickupCreationRequest {

    private String ratePickupIndicator;

    private Shipper shipper;

    private PickupDateInfo pickupDateInfo;

    private PickupAddress PickupAddress;

    private PickupPiece pickupPiece;

    private TotalWeight totalWeight;

    private TrackingData trackingData;

    private String alternateAddressIndicator;

    private String shippingLabelsAvailale;

    private String paymentMethod;
}

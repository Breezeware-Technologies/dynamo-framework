package net.breezeware.dynamo.shipping.ups.dto.pickup;

import lombok.Data;
import net.breezeware.dynamo.shipping.ups.dto.Shipper;

/**
 * PickupCreation Request Dto for set's all the PickupCreation Details.
 * PickupCreationRequest is an parent dto.
 */
@Data
public class PickupCreationRequest {
    /**
     * ratePickupIndicator Indicates whether to rate the on-call pickup or not.
     * .(ratePickupIndicator="Y")
     */
    private String ratePickupIndicator;

    private Shipper shipper;

    private PickupDateInfo pickupDateInfo;

    private PickupAddress PickupAddress;

    private PickupPiece pickupPiece;

    private TotalWeight totalWeight;

    private TrackingData trackingData;
    /**
     * alternateAddressIndicator Indicates if pickup address is a different address
     * than that specified in a customer's profile(eg:alternateAddressIndicator= Y)
     */
    private String alternateAddressIndicator;
    /**
     * shippingLabelsAvailale indicate that user has pre-printed shipping labels for
     * all the packages.
     */
    private String shippingLabelsAvailale;
    /**
     * The payment method to pay for this on call pickup.(eg:00 for No payment,01 =
     * Pay by shipper account)
     */
    private String paymentMethod;
}

package net.breezeware.dynamo.shipping.ups.dto.pickup;

import lombok.Data;

@Data
public class PickupCreationResponse {

    private Response response;

    private String PRN;

    private RateStatus rateStatus;

}

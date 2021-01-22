package net.breezeware.dynamo.shipping.ups.dto.pickup;

import lombok.Data;
import net.breezeware.dynamo.shipping.ups.dto.Response;

@Data
public class PickupCreationResponse {

    private Response response;

    private String PRN;

    private RateStatus rateStatus;

    private RateResult rateResult;

}

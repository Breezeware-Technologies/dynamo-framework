package net.breezeware.dynamo.shipping.ups.dto.pickup;

import lombok.Data;

/**
 * DTO to set the TotalWeight in the PickupCreationRequest.
 */
@Data
public class TotalWeight {

    private String weight;

    private String unitOfMeasurement;
}

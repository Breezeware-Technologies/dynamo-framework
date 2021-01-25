package net.breezeware.dynamo.shipping.ups.dto.pickup;

import lombok.Data;

/**
 * Request Dto to set the Total Weight Of the Packages for the Current Pickup.
 */
@Data
public class TotalWeight {

    private String weight;

    private String unitOfMeasurement;

}

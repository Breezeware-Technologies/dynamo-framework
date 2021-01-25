package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * Response Shipping Dto to persits BillingWeight
 */
@Data
public class BillingWeight {

    private UnitOfMeasurement unitOfMeasurement;

    private String weight;

}

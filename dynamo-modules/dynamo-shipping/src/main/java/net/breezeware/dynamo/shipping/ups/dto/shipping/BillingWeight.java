package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * DTO to persist the BillingWeight for {@link ShipmentResults}.
 */
@Data
public class BillingWeight {

    private UnitOfMeasurement unitOfMeasurement;

    private String weight;

}

package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

@Data
public class BillingWeight {

    private UnitOfMeasurement unitOfMeasurement;

    private String weight;

}

package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

@Data
public class PackageWeight {

    private UnitOfMeasurement unitOfMeasurement;
    private String weight;

}

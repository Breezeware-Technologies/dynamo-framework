package net.breezeware.dynamo.shipping.ups.dto;

import lombok.Data;

@Data
public class PackageWeight {

    private UnitOfMeasurement unitOfMeasurement;
    private String weight;

}

package net.breezeware.dynamo.shipping.dto;

import lombok.Data;

@Data
public class PackageWeight {

    private UnitOfMeasurement unitOfMeasurement;
    private String Weight;

}

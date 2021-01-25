package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * Shipping Request Dto to set the PackageWeight for the single Shipment
 * Request.
 */
@Data
public class PackageWeight {

    private UnitOfMeasurement unitOfMeasurement;
    private String weight;

}

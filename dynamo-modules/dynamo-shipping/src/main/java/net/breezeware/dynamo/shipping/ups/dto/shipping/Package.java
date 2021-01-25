package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * Shipping Request Dto to set the package Details for create single shipment
 * label .
 */
@Data
public class Package {

    private String description;

    private Packaging packaging;

    private PackageWeight packageWeight;

}

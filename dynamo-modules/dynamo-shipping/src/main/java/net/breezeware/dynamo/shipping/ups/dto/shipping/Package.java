package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * DTO to set the Package in the {@link Shipment}.
 */
@Data
public class Package {

    private String description;

    private Packaging packaging;

    private PackageWeight packageWeight;

}

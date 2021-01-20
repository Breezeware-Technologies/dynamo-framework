package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

@Data
public class Package {

    private String description;

    private Packaging packaging;

    private PackageWeight packageWeight;

}

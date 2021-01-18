package net.breezeware.dynamo.shipping.dto;

import lombok.Data;

@Data
public class Package {

    private String Description;

    private Packaging Packaging;

    private PackageWeight PackageWeight;

}

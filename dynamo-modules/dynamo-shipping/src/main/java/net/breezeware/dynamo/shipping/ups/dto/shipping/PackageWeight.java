package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * DTO to set the PackageWeight in the {@link Package}.
 */
@Data
public class PackageWeight {

    private UnitOfMeasurement unitOfMeasurement;
    private String weight;

}

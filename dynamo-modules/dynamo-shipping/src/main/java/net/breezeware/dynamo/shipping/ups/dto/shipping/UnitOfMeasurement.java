package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * The DTO to set the UnitOfMeasurement to {@link PackageWeight}.
 * <p>
 * Use this DTO to persists the UnitOfMeasurement for {@link BillingWeight}.
 * </p>
 */
@Data
public class UnitOfMeasurement {
    /**
     * code value of this String refers the Unit Measurement of the Given
     * package(eg:LBS,KG)
     */
    private String code;

    private String description;

}

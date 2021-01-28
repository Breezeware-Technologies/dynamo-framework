package net.breezeware.dynamo.shipping.ups.dto.tracking;

import lombok.Data;

/**
 * The DTO to Persits the all details For {@link Address}
 */
@Data
public class Address {

    private String city;

    private String stateProvince;

    private String postalCode;

    private String country;

}

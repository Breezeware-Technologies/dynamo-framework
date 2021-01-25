package net.breezeware.dynamo.shipping.ups.dto.tracking;

import lombok.Data;

/**
 * Response Dto to Persits the Address Details
 */
@Data
public class Address {

    private String city;

    private String stateProvince;

    private String postalCode;

    private String country;

}

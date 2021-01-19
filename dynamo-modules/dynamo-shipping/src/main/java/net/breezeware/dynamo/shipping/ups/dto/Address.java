package net.breezeware.dynamo.shipping.ups.dto;

import lombok.Data;

@Data
public class Address {

    private String addressLine;

    private String city;

    private String stateProvinceCode;

    private String postalCode;

    private String countryCode;

}

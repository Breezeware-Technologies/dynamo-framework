package net.breezeware.dynamo.shipping.dto;

import lombok.Data;

@Data
public class Address {

    private String AddressLine;

    private String City;

    private String StateProvinceCode;

    private String PostalCode;

    private String CountryCode;

}

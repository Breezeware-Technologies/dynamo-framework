package net.breezeware.dynamo.shipping.ups.dto.pickup;

import lombok.Data;
import net.breezeware.dynamo.shipping.ups.dto.Phone;

@Data
public class PickupAddress {

    private String companyName;

    private String contactName;

    private String addressLine;

    private String room;

    private String floor;

    private String city;

    private String stateProvince;

    private String urbanization;

    private String postalCode;

    private String countryCode;

    private String residentialIndicator;

    private String pickupPoint;

    private Phone phone;

}

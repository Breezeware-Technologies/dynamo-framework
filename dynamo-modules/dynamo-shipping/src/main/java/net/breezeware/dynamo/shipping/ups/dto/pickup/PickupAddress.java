package net.breezeware.dynamo.shipping.ups.dto.pickup;

import lombok.Data;
import net.breezeware.dynamo.shipping.ups.dto.Phone;

/**
 * DTO to set the PickupAddress for {@link PickupCreationRequest}.
 */
@Data
public class PickupAddress {

    private String companyName;

    private String contactName;

    private String addressLine;

    private String room;

    private String floor;

    private String city;
    /**
     * State Province code(eg:GA)
     */
    private String stateProvince;

    private String urbanization;
    /**
     * Postal Code for the given State
     */
    private String postalCode;
    /**
     * countryCode (eg:US)
     */
    private String countryCode;

    /**
     * residentialIndicator for indicate whether the pickuplocation is on the
     * residentional area.(eg:residentialIndicator="Y")
     */
    private String residentialIndicator;

    private String pickupPoint;

    private Phone phone;

}

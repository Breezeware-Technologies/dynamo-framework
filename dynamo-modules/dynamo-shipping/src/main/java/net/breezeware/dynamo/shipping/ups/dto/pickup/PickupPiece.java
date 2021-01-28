package net.breezeware.dynamo.shipping.ups.dto.pickup;

import lombok.Data;

/**
 * DTO to set the PickupDateInfo in the {@link PickupCreationRequest}.
 */
@Data
public class PickupPiece {
    /**
     * serivce code refers the service availablity in UPS.(eg 003 = UPS Ground)
     */
    private String serviceCode;
    /**
     * number of pieces to be picked up.
     */
    private String quantity;
    /**
     * The destination country (eg:US)
     */
    private String destinationCountryCode;
    /**
     * container type(eg:01 = PACKAGE)
     */
    private String containerCode;

}

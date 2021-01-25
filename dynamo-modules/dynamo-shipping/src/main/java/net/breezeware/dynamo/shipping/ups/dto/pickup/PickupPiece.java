package net.breezeware.dynamo.shipping.ups.dto.pickup;

import lombok.Data;

/**
 * Request Dto for set's the PickupPiece Details
 */
@Data
public class PickupPiece {
    /**
     * serivce Code refers the service availablity in UPS.(eg 003 = UPS Ground)
     */
    private String serviceCode;
    /**
     * Number of pieces to be picked up.
     */
    private String quantity;
    /**
     * The destination country (eg:US)
     */
    private String destinationCountryCode;
    /**
     * Container type(eg:01 = PACKAGE)
     */
    private String containerCode;

}

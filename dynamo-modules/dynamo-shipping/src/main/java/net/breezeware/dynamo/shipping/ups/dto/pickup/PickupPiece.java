package net.breezeware.dynamo.shipping.ups.dto.pickup;

import lombok.Data;

@Data
public class PickupPiece {

    private String serviceCode;

    private String quantity;

    private String destinationCountryCode;

    private String containerCode;

}

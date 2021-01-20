package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

@Data
public class ShipmentResponse {

    private Response response;

    private ShipmentResults shipmentResults;

}

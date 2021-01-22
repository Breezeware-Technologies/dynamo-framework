package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;
import net.breezeware.dynamo.shipping.ups.dto.Response;

@Data
public class ShipmentResponse {

    private Response response;

    private ShipmentResults shipmentResults;

}

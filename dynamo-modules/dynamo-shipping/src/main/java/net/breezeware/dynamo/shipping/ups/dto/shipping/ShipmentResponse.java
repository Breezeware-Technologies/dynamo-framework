package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;
import net.breezeware.dynamo.shipping.ups.dto.Response;

/**
 * The DTO to Persist all properties in ShipmentResponse.
 */
@Data
public class ShipmentResponse {

    private Response response;

    private ShipmentResults shipmentResults;

}

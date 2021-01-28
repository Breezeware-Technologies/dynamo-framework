package net.breezeware.dynamo.shipping.ups.dto;

import lombok.Data;
import net.breezeware.dynamo.shipping.ups.dto.pickup.PickupCreationResponse;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ShipmentResponse;

/**
 * The DTO to persists the {@link Response} for {@link ShipmentResponse} and
 * {@link PickupCreationResponse}.
 */
@Data
public class Response {

    private ResponseStatus responseStatus;

    private String transactionReference;
}

package net.breezeware.dynamo.shipping.ups.dto.tracking;

import java.util.List;

import lombok.Data;

/**
 * The DTO to persists the list of Shipment for {@link TrackResponse}.
 */
@Data
public class TrackResponse {

    private List<Shipment> shipment;

}

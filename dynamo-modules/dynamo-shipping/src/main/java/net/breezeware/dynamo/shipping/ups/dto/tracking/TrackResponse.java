package net.breezeware.dynamo.shipping.ups.dto.tracking;

import java.util.List;

import lombok.Data;

/**
 * parent Response Dto for the Tracking(parent dto)
 */
@Data
public class TrackResponse {

    private List<Shipment> shipment;

}

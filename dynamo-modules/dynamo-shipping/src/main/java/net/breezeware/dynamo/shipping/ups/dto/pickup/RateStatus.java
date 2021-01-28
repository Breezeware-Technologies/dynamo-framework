package net.breezeware.dynamo.shipping.ups.dto.pickup;

import lombok.Data;

/**
 * * DTO to persists all the RateStatus for {@link PickupCreationResponse}.
 */
@Data
public class RateStatus {
    /**
     * code refers rating status. (01= Rate available)
     */
    private String code;

    private String description;

}

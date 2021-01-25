package net.breezeware.dynamo.shipping.ups.dto.pickup;

import lombok.Data;

/**
 * Response dto for the persits the Rate Status
 */
@Data
public class RateStatus {
    /**
     * Code refers rating status. (01= Rate available)
     */
    private String code;

    private String description;

}

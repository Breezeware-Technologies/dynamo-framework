package net.breezeware.dynamo.shipping.ups.dto.tracking;

import lombok.Data;

/**
 * Response Dto to persits the Status Details
 */
@Data
public class Status {
    /**
     * the Status associated to the activities(eg:D=Delivered)
     */
    private String type;

    private String description;

    private String code;

}

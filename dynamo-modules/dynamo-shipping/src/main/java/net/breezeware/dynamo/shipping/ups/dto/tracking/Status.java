package net.breezeware.dynamo.shipping.ups.dto.tracking;

import lombok.Data;

/**
 * The DTO to persits the {@link Status} for {@link Activity}
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

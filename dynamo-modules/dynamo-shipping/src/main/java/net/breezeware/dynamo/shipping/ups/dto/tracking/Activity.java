package net.breezeware.dynamo.shipping.ups.dto.tracking;

import lombok.Data;

/**
 * The DTO to persists the {@link Location} and {@link Status} for
 * {@link Activity}.
 */
@Data
public class Activity {

    private Location location;

    private Status status;

    private String date;

    private String time;

}

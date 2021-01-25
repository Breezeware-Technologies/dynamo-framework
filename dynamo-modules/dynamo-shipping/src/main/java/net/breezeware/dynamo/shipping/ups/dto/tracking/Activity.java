package net.breezeware.dynamo.shipping.ups.dto.tracking;

import lombok.Data;

@Data
public class Activity {

    private Location location;

    private Status status;

    private String date;

    private String time;

}

package net.breezeware.dynamo.shipping.ups.dto.tracking;

import java.util.List;

import lombok.Data;

@Data
public class Activity {

    private List<Location> locations;

    private List<Status> statusList;

    private String date;

    private String time;

}

package net.breezeware.dynamo.shipping.ups.dto.tracking;

import java.util.List;

import lombok.Data;

/**
 * The DTO to pesists the list of {@link Activity} for {@link Package}
 */
@Data
public class Package {

    private String trackingNumber;

    private List<Activity> activity;

}

package net.breezeware.dynamo.shipping.ups.dto;

import lombok.Data;
import net.breezeware.dynamo.shipping.ups.dto.pickup.PickupAddress;

/**
 * The DTO to set the Phone in the {@link Shipper} {@link ShipTo} and
 * {@link PickupAddress}.
 */
@Data
public class Phone {

    private String number;
    /**
     * phone's extension number(eg:911)
     */
    private String extension;

}

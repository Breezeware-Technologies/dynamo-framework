package net.breezeware.dynamo.shipping.ups.dto.tracking;

import lombok.Data;

/**
 * The DTO to persists {@link Address} for {@link Location}
 */
@Data
public class Location {

    private Address address;

}

package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * DTO to set the Packaging in the {@link Package}.
 */
@Data
public class Packaging {
    /**
     * code vaule of the packaging for the specific request(eg:"02",02=Customer
     * Supplied Package) Maximum String Length is 2.
     */
    private String code;

}

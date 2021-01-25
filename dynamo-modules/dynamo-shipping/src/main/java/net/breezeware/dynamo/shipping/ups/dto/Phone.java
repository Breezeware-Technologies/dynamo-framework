package net.breezeware.dynamo.shipping.ups.dto;

import lombok.Data;

/**
 * Set all the Phone Oreiented details in all the shipment&pickup Requests.
 */
@Data
public class Phone {

    private String number;
    /**
     * phone's extension number(eg:911)
     */
    private String extension;

}

package net.breezeware.dynamo.shipping.ups.dto.pickup;

import lombok.Data;
import net.breezeware.dynamo.shipping.ups.dto.Shipper;

/**
 * DTO to set the Account details in the {@link Shipper}
 */
@Data
public class Account {
    /**
     * AccountNumber refers the UPS Account number.
     */
    private String accountNumber;
    /**
     * Account Holder CountryCode (eg:US)
     */
    private String accountCountryCode;

}

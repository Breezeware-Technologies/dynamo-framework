package net.breezeware.dynamo.shipping.ups.dto.pickup;

import lombok.Data;

/**
 * Request Dto for set's the Account Details.
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

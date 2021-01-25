package net.breezeware.dynamo.shipping.ups.dto;

import lombok.Data;
import net.breezeware.dynamo.shipping.ups.dto.pickup.Account;

/**
 * Shipper dto to (source) set the all the details about Shipper.
 */
@Data
public class Shipper {

    private String name;
    /**
     * Shipper’s six digit alphanumeric account number.
     */
    private String shipperNumber;

    private Phone phone;

    private Address address;

    private Account account;

}

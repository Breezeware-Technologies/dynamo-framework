package net.breezeware.dynamo.shipping.ups.dto;

import lombok.Data;
import net.breezeware.dynamo.shipping.ups.dto.pickup.Account;

@Data
public class Shipper {

    private String name;

    private String shipperNumber;

    private Phone phone;

    private Address address;

    private Account account;

}

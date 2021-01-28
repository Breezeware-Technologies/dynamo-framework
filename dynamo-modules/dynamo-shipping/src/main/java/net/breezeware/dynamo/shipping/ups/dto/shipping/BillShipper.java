package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * DTO to set the BillShipper in the {@link ShipmentCharge}.
 */
@Data
public class BillShipper {
    public String accountNumber;
}

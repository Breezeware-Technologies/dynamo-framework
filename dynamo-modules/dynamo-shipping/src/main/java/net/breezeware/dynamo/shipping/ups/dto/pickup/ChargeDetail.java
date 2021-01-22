package net.breezeware.dynamo.shipping.ups.dto.pickup;

import lombok.Data;

@Data
public class ChargeDetail {

    private String ChargeCode;

    private String ChargeDescription;

    private String ChargeAmount;

    private String TaxAmount;
}

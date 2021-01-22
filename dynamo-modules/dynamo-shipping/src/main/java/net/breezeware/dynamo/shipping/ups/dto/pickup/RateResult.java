package net.breezeware.dynamo.shipping.ups.dto.pickup;

import java.util.List;

import lombok.Data;

@Data
public class RateResult {

    private String rateType;

    private String currencyCode;

    private List<ChargeDetail> ChargeDetails;

    private String grandTotalOfAllCharge;

}

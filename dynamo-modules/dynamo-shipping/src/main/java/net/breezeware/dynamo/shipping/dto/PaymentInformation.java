package net.breezeware.dynamo.shipping.dto;

import lombok.Data;

@Data
public class PaymentInformation {

    @Data
    public class ShipmentCharge {
        private String Type;
    }

    @Data
    public class BillShipper {
        private String AccountNumber;
    }

}

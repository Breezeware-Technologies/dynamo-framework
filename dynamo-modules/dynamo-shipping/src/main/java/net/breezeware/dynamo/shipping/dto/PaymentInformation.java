package net.breezeware.dynamo.shipping.dto;

import lombok.Data;

@Data
public class PaymentInformation {

    @Data
    class ShipmentCharge {
        private String Type;
    }

    @Data
    class BillShipper {
        private String AccountNumber;
    }

}

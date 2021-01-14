package net.breezeware.dynamo.shipping.dto;

import lombok.Data;

@Data
public class Shipment {

    private String Description;

    private Shipper Shipper;

    private ShipTo ShipTo;

    private PaymentInformation PaymentInformation;

    private Service Service;

    private Package Package;

    @Data
    class LabelSpecification {

        private String LabelImageFormat;
        private String Code;
    }

}
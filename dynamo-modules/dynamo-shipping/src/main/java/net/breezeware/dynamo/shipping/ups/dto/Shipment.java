package net.breezeware.dynamo.shipping.ups.dto;

import lombok.Data;

@Data
public class Shipment {

    private String description;

    private Shipper shipper;

    private ShipTo shipTo;

    private PaymentInformation paymentInformation;

    private Service service;

    private Package Package;

    private LabelSpecification labelSpecification;

}

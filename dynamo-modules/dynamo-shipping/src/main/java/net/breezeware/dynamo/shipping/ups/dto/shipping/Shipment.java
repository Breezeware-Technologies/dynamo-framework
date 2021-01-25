package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;
import net.breezeware.dynamo.shipping.ups.dto.ShipTo;
import net.breezeware.dynamo.shipping.ups.dto.Shipper;

/**
 * Shipping Request Dto to set the Shipment Details for the single Shipment
 * Request.
 */
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

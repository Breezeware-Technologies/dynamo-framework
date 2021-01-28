package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;
import net.breezeware.dynamo.shipping.ups.dto.ShipTo;
import net.breezeware.dynamo.shipping.ups.dto.Shipper;

/**
 * The Dto to set Shipment Details in the {@link ShipmentRequest}.
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

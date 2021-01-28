package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * DTO to set the LabelSpecification in the {@link Shipment}.
 */
@Data
public class LabelSpecification {

    private LabelImageFormat labelImageFormat;

}

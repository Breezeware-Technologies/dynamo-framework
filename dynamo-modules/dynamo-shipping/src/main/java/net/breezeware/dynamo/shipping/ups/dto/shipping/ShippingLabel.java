package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

@Data
public class ShippingLabel {

    private ImageFormat imageFormat;

    private String graphImage;

    private String HTMLImage;

}

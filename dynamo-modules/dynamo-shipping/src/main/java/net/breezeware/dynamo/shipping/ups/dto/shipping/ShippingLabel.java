package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * Response Dto for Persits ShippingLabel.
 */
@Data
public class ShippingLabel {

    private ImageFormat imageFormat;

    private String graphicImage;

    private String HTMLImage;

}

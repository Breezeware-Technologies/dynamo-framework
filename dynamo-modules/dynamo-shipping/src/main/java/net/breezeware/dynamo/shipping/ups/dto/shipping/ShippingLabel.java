package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * The DTO to Persists ShippingLabel for {@link PackageResults}.
 */
@Data
public class ShippingLabel {

    private ImageFormat imageFormat;

    private String graphicImage;

    private String HTMLImage;

}

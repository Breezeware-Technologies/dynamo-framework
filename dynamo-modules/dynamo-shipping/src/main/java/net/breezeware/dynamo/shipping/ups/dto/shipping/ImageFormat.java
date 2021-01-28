package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * DTO to persist the ImageFormat for {@link ShippingLabel}.
 */
@Data
public class ImageFormat {
    /**
     * code value of image format (eg:Code: "PNG")
     */
    private String code;

    private String description;
}

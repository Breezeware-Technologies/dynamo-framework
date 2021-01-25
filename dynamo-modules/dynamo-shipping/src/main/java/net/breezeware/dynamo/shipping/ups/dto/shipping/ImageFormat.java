package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * Response Dto for Persits Image Format Details.
 */
@Data
public class ImageFormat {
    /**
     * Persits Code value (eg:Code: "PNG")
     */
    private String code;

    private String description;
}

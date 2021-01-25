package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * Shipping Request Dto to set the Label Image Format.
 */
@Data
public class LabelImageFormat {
    /**
     * code value of labelImageFormat(eg:PNG,GIF)
     */
    private String code;

}

package net.breezeware.dynamo.shipping.ups.dto.shipping;

import lombok.Data;

/**
 * DTO to set the LabelImageFormat in the {@link LabelSpecification}.
 */
@Data
public class LabelImageFormat {
    /**
     * code value of label image format(eg:PNG,GIF)
     */
    private String code;

}

package net.breezeware.dynamo.shipping.ups.dto;

import lombok.Data;

/**
 * Persist all the ResponseStatus values
 */

@Data
public class ResponseStatus {
    /**
     * code value Represnts success/failure codes.
     */
    private String code;

    private String description;

}

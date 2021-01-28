package net.breezeware.dynamo.shipping.ups.dto;

import lombok.Data;

/**
 * The DTO to persists the {@link ResponseStatus} for {@link Response}.
 */

@Data
public class ResponseStatus {
    /**
     * code value Represnts success/failure codes.
     */
    private String code;

    private String description;

}

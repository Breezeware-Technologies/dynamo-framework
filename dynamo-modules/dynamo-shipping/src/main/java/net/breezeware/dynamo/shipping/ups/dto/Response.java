package net.breezeware.dynamo.shipping.ups.dto;

import lombok.Data;

/**
 * Response dto Persits all the details.
 */
@Data
public class Response {

    private ResponseStatus responseStatus;

    private String transactionReference;
}

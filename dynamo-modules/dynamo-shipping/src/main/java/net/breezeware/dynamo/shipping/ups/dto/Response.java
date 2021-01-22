package net.breezeware.dynamo.shipping.ups.dto;

import lombok.Data;

@Data
public class Response {

    private ResponseStatus responseStatus;

    private String transactionReference;
}

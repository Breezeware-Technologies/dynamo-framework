package net.breezeware.dynamo.shipping.ups.dto.tracking;

import lombok.Data;

@Data
public class Status {

    private String type;
    private String description;

    private String code;

}

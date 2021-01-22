package net.breezeware.dynamo.shipping.ups.dto.tracking;

import java.util.List;

import lombok.Data;

@Data
public class Shipment {
    private List<Package> packages;
}

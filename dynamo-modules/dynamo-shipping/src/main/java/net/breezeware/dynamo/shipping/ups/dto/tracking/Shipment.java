package net.breezeware.dynamo.shipping.ups.dto.tracking;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * The DTO to persits the list of {@link Package} for {@link Shipment}
 */
@Data
public class Shipment {

    @SerializedName("package")
    private List<Package> packageProperty;

}

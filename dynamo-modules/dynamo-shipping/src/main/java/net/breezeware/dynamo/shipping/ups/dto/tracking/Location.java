package net.breezeware.dynamo.shipping.ups.dto.tracking;

import java.util.List;

import lombok.Data;
import net.breezeware.dynamo.shipping.ups.dto.Address;

@Data
public class Location {

    private List<Address> addressList;
    

}

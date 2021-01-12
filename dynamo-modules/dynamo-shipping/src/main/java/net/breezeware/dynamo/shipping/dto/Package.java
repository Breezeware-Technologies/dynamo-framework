package net.breezeware.dynamo.shipping.dto;

import lombok.Data;

@Data
public class Package {

    private String Description;

    @Data
    class Packaging {
        private String Code;

    }

    @Data
    class PacakgeWeight {

        @Data
        class UnitOfMeasurement {
            private String Code;
        }

        private String Weight;

    }

}

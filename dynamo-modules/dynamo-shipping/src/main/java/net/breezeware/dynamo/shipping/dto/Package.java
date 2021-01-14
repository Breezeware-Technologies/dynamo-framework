package net.breezeware.dynamo.shipping.dto;

import lombok.Data;

@Data
public class Package {

    private String Description;

    @Data
    public class Packaging {
        private String Code;

    }

    @Data
    public class PackageWeight {

        @Data
        public class UnitOfMeasurement {
            private String Code;
        }

        private String Weight;

    }

}

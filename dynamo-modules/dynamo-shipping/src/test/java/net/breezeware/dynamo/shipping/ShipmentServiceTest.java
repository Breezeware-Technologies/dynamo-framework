package net.breezeware.dynamo.shipping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.dynamo.shipping.dto.ShipmentRequest;
import net.breezeware.dynamo.shipping.service.api.ShipmentService;

@Slf4j
@ContextConfiguration(classes = TestApplication.class)
public class ShipmentServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    ShipmentService shipmentService;

    @org.junit.jupiter.api.Test
    public void CreatelabelRequestTest() {

        log.info("Entering CreateLabelReqestTest");

        // ShipmentRequest shipmentRequest = new ShipmentRequest();

        ShipmentRequest shipmentRequest1 = shipmentService.populateShippingLabelCreation();
        if (shipmentRequest1 != null) {
            shipmentService.CreateShippingLabel(shipmentRequest1);
        } else {
            log.info("null exception");

        }
        log.info("leaving CreateLabelReqestTest {}", shipmentRequest1);

    }

}

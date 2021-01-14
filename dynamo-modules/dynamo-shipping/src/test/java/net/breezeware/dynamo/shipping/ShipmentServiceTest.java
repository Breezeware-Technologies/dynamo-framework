package net.breezeware.dynamo.shipping;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.dynamo.shipping.dto.Address;
import net.breezeware.dynamo.shipping.dto.Package;
import net.breezeware.dynamo.shipping.dto.Package.PackageWeight;
import net.breezeware.dynamo.shipping.dto.Package.Packaging;
import net.breezeware.dynamo.shipping.dto.PaymentInformation;
import net.breezeware.dynamo.shipping.dto.PaymentInformation.BillShipper;
import net.breezeware.dynamo.shipping.dto.PaymentInformation.ShipmentCharge;
import net.breezeware.dynamo.shipping.dto.Phone;
import net.breezeware.dynamo.shipping.dto.ShipTo;
import net.breezeware.dynamo.shipping.dto.Shipment;
import net.breezeware.dynamo.shipping.dto.ShipmentRequest;
import net.breezeware.dynamo.shipping.dto.Shipper;
import net.breezeware.dynamo.shipping.service.api.ShipmentService;

@Slf4j
@ContextConfiguration(classes = TestApplication.class)
public class ShipmentServiceTest {

    @Autowired
    ShipmentService shipmentService;

    @Test
    public void CreatelabelRequestTest() {

        log.info("Entering CreateLabelReqestTest");

        // ShipmentRequest shipmentRequest = new ShipmentRequest();

        ShipmentRequest shipmentRequest = new ShipmentRequest();

        // Shipment shipment = new Shipment();
        Shipment shipment = shipmentService.populatShipmentDto();
        // shipmentService.populatShipmentDto();
        // Address address = populateShipperAddress();
        // Phone phone = populateShipperPhoneDetails();
        // Shipper shipper = populateShipperDetails(address, phone);
        // PaymentInformation paymentInformation = populatePaymentInfo();
        // Package _package = populatePackageDetails();
        // ShipTo shipTo = populateShipToDetails();
        // net.breezeware.dynamo.shipping.dto.Service service =
        // populateServiceDetails();
        // shipment.setDescription("hai");
        // shipment.setPackage(_package);
        // shipment.setPaymentInformation(paymentInformation);
        // shipment.setService(service);
        // shipment.setShipper(shipper);
        // shipment.setShipTo(shipTo);

        shipmentRequest = shipmentService.populateShippingLabelCreation(shipment);

        // shipmentRequest.setShipment(shipment);

        log.info("Shipment request {}", shipmentRequest);

        String result = shipmentService.CreateShippingLabel(shipmentRequest);
        log.info("leaving CreateLabelReqestTest {}", result);

    }

    // public Shipment populateShippingLabelCreation() {
    //
    // ShipmentRequest shipmentRequest = new ShipmentRequest();
    //
    // Shipment shipment = new Shipment();
    // Address address = populateShipperAddress();
    // Phone phone = populateShipperPhoneDetails();
    // Shipper shipper = populateShipperDetails(address, phone);
    // PaymentInformation paymentInformation = populatePaymentInfo();
    // Package _package = populatePackageDetails();
    // ShipTo shipTo = populateShipToDetails();
    // net.breezeware.dynamo.shipping.dto.Service service =
    // populateServiceDetails();
    //
    // shipment.setDescription("MediKit");
    // shipment.setShipper(shipper);
    // shipment.setShipTo(shipTo);
    // shipment.setPaymentInformation(paymentInformation);
    // shipment.setService(service);
    // shipment.setPackage(_package);
    //
    // shipmentRequest.setShipment(shipment);
    //
    // return shipment;
    // }
    //
    // private Shipper populateShipperDetails(Address address, Phone phone) {
    //
    // Shipper shipper = new Shipper();
    //
    // shipper.setAddress(address);
    // shipper.setName("breeze");
    // shipper.setPhone(phone);
    // shipper.setShipperNumber("34V933");
    //
    // return shipper;
    //
    // }
    //
    // private ShipTo populateShipToDetails() {
    //
    // ShipTo shipTo = new ShipTo();
    //
    // shipTo.setName("john");
    //
    // Address address = new Address();
    // address.setAddressLine("usa");
    // address.setCity("georgia");
    // address.setCountryCode("US");
    // address.setPostalCode("30004");
    // address.setStateProvinceCode("GA");
    //
    // shipTo.setAddress(address);
    // Phone phone = new Phone();
    // phone.setNumber("96566455454");
    //
    // shipTo.setPhone(phone);
    // return shipTo;
    // }
    //
    // private Address populateShipperAddress() {
    //
    // Address address = new Address();
    //
    // address.setAddressLine("usa");
    // address.setCity("georgia");
    // address.setCountryCode("US");
    // address.setPostalCode("30004");
    // address.setStateProvinceCode("GA");
    // return address;
    //
    // }
    //
    // private Phone populateShipperPhoneDetails() {
    //
    // Phone phone = new Phone();
    //
    // phone.setNumber("1234567890");
    // return phone;
    //
    // }
    //
    // private PaymentInformation populatePaymentInfo() {
    //
    // PaymentInformation paymentInformation = new PaymentInformation();
    //
    // BillShipper billShipper = paymentInformation.new BillShipper();
    //
    // ShipmentCharge shipmentCharge = paymentInformation.new ShipmentCharge();
    //
    // billShipper.setAccountNumber("34V933");
    // shipmentCharge.setType("01");
    //
    // return paymentInformation;
    //
    // }
    //
    // private Package populatePackageDetails() {
    //
    // Package _package = new Package();
    //
    // _package.setDescription("medical Goods");
    //
    // PackageWeight pacakgeWeight = _package.new PackageWeight();
    // Packaging packaging = _package.new Packaging();
    // packaging.setCode("02");
    //
    // PackageWeight.UnitOfMeasurement unitOfMeasurement = pacakgeWeight.new
    // UnitOfMeasurement();
    // unitOfMeasurement.setCode("LBS");
    // pacakgeWeight.setWeight("45");
    //
    // return _package;
    // }
    //
    // private net.breezeware.dynamo.shipping.dto.Service populateServiceDetails() {
    //
    // net.breezeware.dynamo.shipping.dto.Service service = new
    // net.breezeware.dynamo.shipping.dto.Service();
    //
    // service.setCode("03");
    // service.setDescription("forward postal service");
    //
    // return service;
    // }
}

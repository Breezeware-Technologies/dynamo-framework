package net.breezeware.dynamo.shipping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.dynamo.shipping.dto.Address;
import net.breezeware.dynamo.shipping.dto.BillShipper;
import net.breezeware.dynamo.shipping.dto.LabelImageFormat;
import net.breezeware.dynamo.shipping.dto.LabelSpecification;
import net.breezeware.dynamo.shipping.dto.Package;
import net.breezeware.dynamo.shipping.dto.PackageWeight;
import net.breezeware.dynamo.shipping.dto.Packaging;
import net.breezeware.dynamo.shipping.dto.PaymentInformation;
import net.breezeware.dynamo.shipping.dto.Phone;
import net.breezeware.dynamo.shipping.dto.ShipTo;
import net.breezeware.dynamo.shipping.dto.Shipment;
import net.breezeware.dynamo.shipping.dto.ShipmentCharge;
import net.breezeware.dynamo.shipping.dto.ShipmentRequest;
import net.breezeware.dynamo.shipping.dto.Shipper;
import net.breezeware.dynamo.shipping.dto.UnitOfMeasurement;
import net.breezeware.dynamo.shipping.service.api.ShipmentService;
import net.breezeware.dynamo.shipping.service.impl.ShipmentServiceImpl;

@Slf4j
public class ShipmentServiceTest {

    ShipmentService shipmentService;

    @BeforeEach
    public void setupMethod() {

        shipmentService = new ShipmentServiceImpl();

    }

    @Test
    public void createlabelRequestTest() {

        log.info("Entering CreateLabelReqestTest");

        // ShipmentRequest shipmentRequest = new ShipmentRequest();

        ShipmentRequest shipmentRequest = populateShippingLabelCreation();

        // Shipment shipment = new Shipment();
        // Shipment shipment = shipmentService.populatShipmentDto();
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

        // shipmentRequest.setShipment(shipment);

        log.info("Shipment request {}", shipmentRequest);
        System.out.println(shipmentRequest);
        String result = shipmentService.createShippingLabel(shipmentRequest);
        System.out.println(result);

        log.info("leaving CreateLabelReqestTest {}", result);

    }

    private ShipmentRequest populateShippingLabelCreation() {

        ShipmentRequest shipmentRequest = new ShipmentRequest();

        Shipment shipment = new Shipment();
        Address address = populateShipperAddress();
        Phone phone = populateShipperPhoneDetails();
        Shipper shipper = populateShipperDetails(address, phone);
        PaymentInformation paymentInformation = populatePaymentInfo();
        Package _package = populatePackageDetails();
        ShipTo shipTo = populateShipToDetails();
        LabelSpecification labelSpecification = populateLabelSpecification();

        net.breezeware.dynamo.shipping.dto.Service service = populateServiceDetails();

        shipment.setDescription("MediKit");
        shipment.setShipper(shipper);
        shipment.setShipTo(shipTo);
        shipment.setPaymentInformation(paymentInformation);
        shipment.setService(service);
        shipment.setPackage(_package);
        shipment.setLabelSpecification(labelSpecification);

        shipmentRequest.setShipment(shipment);

        return shipmentRequest;
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

    private Shipper populateShipperDetails(Address address, Phone phone) {

        Shipper shipper = new Shipper();

        shipper.setAddress(address);
        shipper.setName("breeze");
        shipper.setPhone(phone);
        shipper.setShipperNumber("34V933");

        return shipper;

    }

    private ShipTo populateShipToDetails() {

        ShipTo shipTo = new ShipTo();

        shipTo.setName("john");

        Address address = new Address();
        address.setAddressLine("usa");
        address.setCity("georgia");
        address.setCountryCode("US");
        address.setPostalCode("30004");
        address.setStateProvinceCode("GA");

        shipTo.setAddress(address);
        Phone phone = new Phone();
        phone.setNumber("96566455454");

        shipTo.setPhone(phone);
        return shipTo;
    }

    private Address populateShipperAddress() {

        Address address = new Address();

        address.setAddressLine("usa");
        address.setCity("georgia");
        address.setCountryCode("US");
        address.setPostalCode("30004");
        address.setStateProvinceCode("GA");
        return address;

    }

    private Phone populateShipperPhoneDetails() {

        Phone phone = new Phone();

        phone.setNumber("1234567890");
        return phone;

    }

    private PaymentInformation populatePaymentInfo() {

        PaymentInformation paymentInformation = new PaymentInformation();

        BillShipper billShipper = new BillShipper();
        billShipper.setAccountNumber("34V933");

        ShipmentCharge shipmentCharge = new ShipmentCharge();
        shipmentCharge.setType("01");

        paymentInformation.setBillShipper(billShipper);
        paymentInformation.setShipmentCharge(shipmentCharge);

        return paymentInformation;

    }

    private Package populatePackageDetails() {

        Package _package = new Package();

        _package.setDescription("medical Goods");

        Packaging packaging = new Packaging();
        packaging.setCode("02");

        PackageWeight packageWeight = new PackageWeight();

        UnitOfMeasurement unitOfMeasurement = new UnitOfMeasurement();
        unitOfMeasurement.setCode("LBS");
        packageWeight.setWeight("45");
        packageWeight.setUnitOfMeasurement(unitOfMeasurement);

        _package.setPackageWeight(packageWeight);
        _package.setPackaging(packaging);

        return _package;
    }

    private net.breezeware.dynamo.shipping.dto.Service populateServiceDetails() {

        net.breezeware.dynamo.shipping.dto.Service service = new net.breezeware.dynamo.shipping.dto.Service();

        service.setCode("03");
        service.setDescription("forward postal service");

        return service;
    }

    private LabelSpecification populateLabelSpecification() {

        LabelSpecification labelSpecification = new LabelSpecification();

        LabelImageFormat labelImageFormat = new LabelImageFormat();

        labelImageFormat.setCode("PNG");
        labelSpecification.setLabelImageFormat(labelImageFormat);
        return labelSpecification;
    }
    // NOTE: request parsed data

    // {"shipment":{"Description":"MediKit","Shipper":{"Name":"breeze","ShipperNumber":"34V933","Phone":{"Number":"1234567890"},"Address":{"AddressLine":"usa","City":"georgia","StateProvinceCode":"GA","PostalCode":"30004","CountryCode":"US"}},"ShipTo":{"Name":"john","Phone":{"Number":"96566455454"},"Address":{"AddressLine":"usa","City":"georgia","StateProvinceCode":"GA","PostalCode":"30004","CountryCode":"US"}},"PaymentInformation":{"shipmentCharge":{"Type":"01"},"billShipper":{"AccountNumber":"34V933"}},"Service":{"Code":"03","Description":"forward
    // postal service"},"Package":{"Description":"medical
    // Goods","Packaging":{"Code":"02"},"PackageWeight":{"unitOfMeasurement":{"Code":"LBS"},"Weight":"45"}},"labelSpecification":{"labelImageFormat":{"Code":"PNG"}}}}

}

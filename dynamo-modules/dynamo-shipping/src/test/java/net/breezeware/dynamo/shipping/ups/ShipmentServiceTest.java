package net.breezeware.dynamo.shipping.ups;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.dynamo.shipping.ups.dto.Address;
import net.breezeware.dynamo.shipping.ups.dto.Phone;
import net.breezeware.dynamo.shipping.ups.dto.ShipTo;
import net.breezeware.dynamo.shipping.ups.dto.Shipper;
import net.breezeware.dynamo.shipping.ups.dto.pickup.Account;
import net.breezeware.dynamo.shipping.ups.dto.pickup.PickupAddress;
import net.breezeware.dynamo.shipping.ups.dto.pickup.PickupCreationRequest;
import net.breezeware.dynamo.shipping.ups.dto.pickup.PickupCreationResponse;
import net.breezeware.dynamo.shipping.ups.dto.pickup.PickupDateInfo;
import net.breezeware.dynamo.shipping.ups.dto.pickup.PickupPiece;
import net.breezeware.dynamo.shipping.ups.dto.pickup.TotalWeight;
import net.breezeware.dynamo.shipping.ups.dto.pickup.TrackingData;
import net.breezeware.dynamo.shipping.ups.dto.shipping.BillShipper;
import net.breezeware.dynamo.shipping.ups.dto.shipping.LabelImageFormat;
import net.breezeware.dynamo.shipping.ups.dto.shipping.LabelSpecification;
import net.breezeware.dynamo.shipping.ups.dto.shipping.Package;
import net.breezeware.dynamo.shipping.ups.dto.shipping.PackageWeight;
import net.breezeware.dynamo.shipping.ups.dto.shipping.Packaging;
import net.breezeware.dynamo.shipping.ups.dto.shipping.PaymentInformation;
import net.breezeware.dynamo.shipping.ups.dto.shipping.Service;
import net.breezeware.dynamo.shipping.ups.dto.shipping.Shipment;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ShipmentCharge;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ShipmentRequest;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ShipmentResponse;
import net.breezeware.dynamo.shipping.ups.dto.shipping.UnitOfMeasurement;
import net.breezeware.dynamo.shipping.ups.dto.tracking.TrackResponse;
import net.breezeware.dynamo.shipping.ups.service.api.ShipmentService;
import net.breezeware.dynamo.shipping.ups.service.impl.ShipmentServiceImpl;

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

        ShipmentRequest shipmentRequest = populateShippingLabelCreation();

        log.info("Shipment request {}", shipmentRequest);
        System.out.println(shipmentRequest);
        ShipmentResponse result = shipmentService.createShippingLabel(shipmentRequest);
        System.out.println(result);

        log.info("leaving CreateLabelReqestTest {}", result);

    }

    @Test
    public void pickupCreationtest() {

        PickupCreationRequest pickupCreationRequest = populatePickupCreationRequest();

        System.out.println(pickupCreationRequest);

        PickupCreationResponse pickupCreationResponse = shipmentService.pickupCreation(pickupCreationRequest);
        System.out.println(pickupCreationResponse);
    }

    @Test
    public void trackingResponseTest() {

        String trackingNumber = "1Z5338FF0107231059";

        TrackResponse trackResponse = shipmentService.getTrackingDetails(trackingNumber);

        System.out.println("result" + trackResponse);

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

        Service service = populateServiceDetails();

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

    private Shipper populateShipperDetails(Address address, Phone phone) {

        Shipper shipper = new Shipper();

        shipper.setName("breeze");
        shipper.setShipperNumber("34V933");
        shipper.setAddress(address);
        shipper.setPhone(phone);

        return shipper;

    }

    private ShipTo populateShipToDetails() {

        ShipTo shipTo = new ShipTo();

        shipTo.setName("john");

        Address address = new Address();
        address.setAddressLine("usa");
        address.setCity("city");
        address.setCountryCode("US");
        address.setPostalCode("30003");
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
        address.setCity("city");
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
        shipmentCharge.setBillShipper(billShipper);
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

    private Service populateServiceDetails() {

        Service service = new Service();

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

    private PickupCreationRequest populatePickupCreationRequest() {

        PickupCreationRequest pickupCreationRequest = new PickupCreationRequest();

        PickupAddress pickupAddress = populatePickupAddress();

        Shipper shipper = populateShipperForPickup();

        PickupDateInfo pickupDateInfo = populatePickupInfo();

        PickupPiece pickupPiece = populatePickupPieceInfo();

        TotalWeight totalWeight = populateTotalWeight();

        TrackingData trackingData = populateTrackingData();

        pickupCreationRequest.setAlternateAddressIndicator("Y");
        pickupCreationRequest.setPaymentMethod("01");
        pickupCreationRequest.setRatePickupIndicator("y");
        pickupCreationRequest.setPickupAddress(pickupAddress);
        pickupCreationRequest.setPickupDateInfo(pickupDateInfo);
        pickupCreationRequest.setPickupPiece(pickupPiece);
        pickupCreationRequest.setShipper(shipper);
        pickupCreationRequest.setShippingLabelsAvailale("Y");
        pickupCreationRequest.setTotalWeight(totalWeight);
        pickupCreationRequest.setTrackingData(trackingData);

        return pickupCreationRequest;
    }

    private PickupAddress populatePickupAddress() {

        PickupAddress pickupAddress = new PickupAddress();

        Phone phone = new Phone();
        phone.setExtension("911");
        phone.setNumber("8428603545");

        pickupAddress.setAddressLine("315 Saddle Bridge Drive");
        pickupAddress.setCity("georgia");
        pickupAddress.setCompanyName("Refresh Health");
        pickupAddress.setContactName("mr.steve");
        pickupAddress.setCountryCode("US");
        pickupAddress.setFloor("2");
        pickupAddress.setPhone(phone);
        pickupAddress.setPickupPoint("Lobby");
        pickupAddress.setPostalCode("30004");
        pickupAddress.setResidentialIndicator("Y");
        pickupAddress.setRoom("R01");
        pickupAddress.setStateProvince("GA");
        pickupAddress.setUrbanization("");
        return pickupAddress;
    }

    private Shipper populateShipperForPickup() {

        Shipper shipper = new Shipper();
        Account account = new Account();

        account.setAccountCountryCode("US");
        account.setAccountNumber("34V933");
        shipper.setAccount(account);
        return shipper;

    }

    private PickupDateInfo populatePickupInfo() {

        PickupDateInfo pickupDateInfo = new PickupDateInfo();

        pickupDateInfo.setCloseTime("1400");
        pickupDateInfo.setReadyTime("0500");
        pickupDateInfo.setPickupDate("20200110");
        return pickupDateInfo;

    }

    private PickupPiece populatePickupPieceInfo() {

        PickupPiece pickupPiece = new PickupPiece();

        pickupPiece.setContainerCode("01");
        pickupPiece.setDestinationCountryCode("US");
        pickupPiece.setQuantity("1");
        pickupPiece.setServiceCode("003");

        return pickupPiece;
    }

    private TotalWeight populateTotalWeight() {

        TotalWeight totalWeight = new TotalWeight();
        totalWeight.setUnitOfMeasurement("LBS");
        totalWeight.setWeight("5.5");

        return totalWeight;

    }

    private TrackingData populateTrackingData() {
        TrackingData trackingData = new TrackingData();
        trackingData.setTrackingNumber("1Z34V9330300009013");
        return trackingData;

    }
}

package net.breezeware.dynamo.shipping.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;

import lombok.Getter;
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

@Service
@Slf4j
public class ShipmentServiceImpl implements ShipmentService {

    @Getter
    @Value("${ups.username}")
    private String upsUserName;

    @Getter
    @Value("${ups.password}")
    private String upsPassword;

    @Getter
    @Value("${ups.accesskey}")
    private String upsAccessKey;

    @Getter
    @Value("${ups.accountnumber}")
    private String upsAccountNumber;

    @Getter
    @Value("${ups.shipping.createlabel.testing.uri}")
    private String ShippingLabelCreationUrl;

    private String makeLabelCreationCall(String requestBody) {
        String LabelCreationUrl = ShippingLabelCreationUrl;

        WebClient client = WebClient.create(LabelCreationUrl);
        // "UserName" ,upsUserName ).header("Password", upsPassword).header().

        ResponseEntity<String> result = client.get().headers(httpHeaders -> {
            httpHeaders.set("Username", upsUserName);
            httpHeaders.set("Password", upsPassword);
            httpHeaders.set("AccessLicenseNumber", upsAccessKey);
        }).exchange().flatMap(response -> response.toEntity(String.class)).block();

        log.info("The Response Status Code = {}", result.getBody());

        if (result.getStatusCode().equals(HttpStatus.OK)) {
            return result.getBody();
        } else {
            return "badResponse";
        }
    }

    @Override
    public String CreateShippingLabel(ShipmentRequest shipmentRequest) {
        log.info("CreateShippingLabel = {}", shipmentRequest);
        Shipment shipment = populatShipmentDto();
        shipmentRequest = populateShippingLabelCreation(shipment);

        Gson gson = new Gson();
        String requestBody = gson.toJson(shipmentRequest);
        log.info("Request Body as JSON = {}", requestBody);
        return makeLabelCreationCall(requestBody);
    }

    @Override
    public ShipmentRequest populateShippingLabelCreation(Shipment shipment) {
        // shipment = populatShipmentDto();

        ShipmentRequest shipmentRequest = new ShipmentRequest();
        shipmentRequest.setShipment(shipment);
        return shipmentRequest;
    }

    @Override
    public Shipment populatShipmentDto() {

        Shipment shipment = new Shipment();
        Address address = populateShipperAddress();
        Phone phone = populateShipperPhoneDetails();
        Shipper shipper = populateShipperDetails(address, phone);
        PaymentInformation paymentInformation = populatePaymentInfo();
        Package _package = populatePackageDetails();
        ShipTo shipTo = populateShipToDetails();
        net.breezeware.dynamo.shipping.dto.Service service = populateServiceDetails();

        shipment.setDescription("MediKit");
        shipment.setShipper(shipper);
        shipment.setShipTo(shipTo);
        shipment.setPaymentInformation(paymentInformation);
        shipment.setService(service);
        shipment.setPackage(_package);
        return shipment;

    }

    private Shipper populateShipperDetails(Address address, Phone phone) {

        Shipper shipper = new Shipper();

        shipper.setAddress(address);
        shipper.setName("breeze");
        shipper.setPhone(phone);
        shipper.setShipperNumber(upsAccountNumber);

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

        BillShipper billShipper = paymentInformation.new BillShipper();

        ShipmentCharge shipmentCharge = paymentInformation.new ShipmentCharge();

        billShipper.setAccountNumber(upsAccountNumber);
        shipmentCharge.setType("01");

        return paymentInformation;

    }

    private Package populatePackageDetails() {

        Package _package = new Package();

        _package.setDescription("medical Goods");

        PackageWeight pacakgeWeight = _package.new PackageWeight();
        Packaging packaging = _package.new Packaging();
        packaging.setCode("02");

        PackageWeight.UnitOfMeasurement unitOfMeasurement = pacakgeWeight.new UnitOfMeasurement();
        unitOfMeasurement.setCode("LBS");
        pacakgeWeight.setWeight("45");

        return _package;
    }

    private net.breezeware.dynamo.shipping.dto.Service populateServiceDetails() {

        net.breezeware.dynamo.shipping.dto.Service service = new net.breezeware.dynamo.shipping.dto.Service();

        service.setCode("03");
        service.setDescription("forward postal service");

        return service;
    }

}

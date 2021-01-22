package net.breezeware.dynamo.shipping.ups.service.impl;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.dynamo.shipping.ups.dto.Response;
import net.breezeware.dynamo.shipping.ups.dto.ResponseStatus;
import net.breezeware.dynamo.shipping.ups.dto.pickup.ChargeDetail;
import net.breezeware.dynamo.shipping.ups.dto.pickup.PickupCreationRequest;
import net.breezeware.dynamo.shipping.ups.dto.pickup.PickupCreationResponse;
import net.breezeware.dynamo.shipping.ups.dto.pickup.RateResult;
import net.breezeware.dynamo.shipping.ups.dto.pickup.RateStatus;
import net.breezeware.dynamo.shipping.ups.dto.shipping.BillingWeight;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ImageFormat;
import net.breezeware.dynamo.shipping.ups.dto.shipping.PackageResults;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ServiceOptionsCharges;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ShipmentCharges;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ShipmentRequest;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ShipmentResponse;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ShipmentResults;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ShippingLabel;
import net.breezeware.dynamo.shipping.ups.dto.shipping.TotalCharges;
import net.breezeware.dynamo.shipping.ups.dto.shipping.TransportationCharges;
import net.breezeware.dynamo.shipping.ups.dto.shipping.UnitOfMeasurement;
import net.breezeware.dynamo.shipping.ups.dto.tracking.TrackResponse;
import net.breezeware.dynamo.shipping.ups.service.api.ShipmentService;

@Service
@Slf4j
public class ShipmentServiceImpl implements ShipmentService {

    private final String upsUserName = "refreshhealth";

    private final String upsPassword = "Refresh123!";

    private final String upsAccessKey = "1D925021583D91B2";

    private String makeLabelCreationCall(String requestBody) {
        String LabelCreationUrl = "https://wwwcie.ups.com/ship/v1/shipments";

        WebClient client = WebClient.create(LabelCreationUrl);

        ResponseEntity<String> result = client.post().headers(httpHeaders -> {
            httpHeaders.set("Username", upsUserName);
            httpHeaders.set("Password", upsPassword);
            httpHeaders.set("AccessLicenseNumber", upsAccessKey);
        }).contentType(MediaType.APPLICATION_JSON).bodyValue(requestBody).exchange()
                .flatMap(response -> response.toEntity(String.class)).block();

        log.info("The Response Status Code = {}", result.toString());
        System.out.println("The Response Status Code" + result);

        if (result.getStatusCodeValue() == 200) {
            return result.getBody();
        } else {
            return "badResponse";
        }
    }

    public ShipmentResponse createShippingLabel(ShipmentRequest shipmentRequest) {
        log.info("createShippingLabel = {}", shipmentRequest);

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

        JsonElement je = gson.toJsonTree(shipmentRequest);
        JsonObject jo = new JsonObject();

        jo.add("ShipmentRequest", je);
        String requestBody = jo.toString();
        System.out.println("requestBody" + requestBody);
        log.info("Request Body as JSON = {}", requestBody);
        String response = makeLabelCreationCall(requestBody);

        System.out.println("response " + response);
        ShipmentResponse shipmentResponse = persitsResponseDataToDtos(response);

        return shipmentResponse;
    }

    private ShipmentResponse persitsResponseDataToDtos(String response) {

        Response responseDto = populateResponse(response);
        ShipmentResults shipmentResults = populateShipmentResults(response);

        ShipmentResponse shipmentResponseDto = new ShipmentResponse();
        shipmentResponseDto.setResponse(responseDto);
        shipmentResponseDto.setShipmentResults(shipmentResults);
        return shipmentResponseDto;
    }

    private Response populateResponse(String response) {
        JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);

        JsonElement shipmentresponse = jsonObject.get("ShipmentResponse");

        JsonElement responseElement = shipmentresponse.getAsJsonObject().get("Response");

        JsonElement responseStatus = responseElement.getAsJsonObject().get("ResponseStatus");

        JsonElement transactionReference = responseElement.getAsJsonObject().get("TransactionReference");

        JsonElement code = responseStatus.getAsJsonObject().get("Code");

        JsonElement description = responseStatus.getAsJsonObject().get("Description");

        ResponseStatus _ResponseStatus = new ResponseStatus();
        _ResponseStatus.setCode(code.getAsString());
        _ResponseStatus.setDescription(description.getAsString());

        Response _Response = new Response();
        _Response.setResponseStatus(_ResponseStatus);
        _Response.setTransactionReference(transactionReference.getAsString());

        return _Response;

    }

    private ShipmentResults populateShipmentResults(String response) {

        JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);
        JsonElement shipmentresponse = jsonObject.get("ShipmentResponse");

        JsonElement shipmentResults = shipmentresponse.getAsJsonObject().get("ShipmentResults");

        JsonElement shipmentCharges = shipmentResults.getAsJsonObject().get("ShipmentCharges");

        JsonElement shipmentIdentificationNumber = shipmentResults.getAsJsonObject()
                .get("ShipmentIdentificationNumber");
        JsonElement transportationCharges = shipmentCharges.getAsJsonObject().get("TransportationCharges");
        JsonElement currencyCode = transportationCharges.getAsJsonObject().get("CurrencyCode");
        JsonElement monetaryValue = transportationCharges.getAsJsonObject().get("MonetaryValue");

        JsonElement serviceOptionsCharges = shipmentCharges.getAsJsonObject().get("ServiceOptionsCharges");
        JsonElement currencyCodeSoc = serviceOptionsCharges.getAsJsonObject().get("CurrencyCode");
        JsonElement monetaryValueSoc = serviceOptionsCharges.getAsJsonObject().get("MonetaryValue");

        JsonElement totalCharges = shipmentCharges.getAsJsonObject().get("TotalCharges");
        JsonElement currencyCodeTc = totalCharges.getAsJsonObject().get("CurrencyCode");
        JsonElement monetaryValueTc = totalCharges.getAsJsonObject().get("MonetaryValue");

        JsonElement billingWeight = shipmentResults.getAsJsonObject().get("BillingWeight");
        JsonElement unitOfMeasurement = billingWeight.getAsJsonObject().get("UnitOfMeasurement");
        JsonElement codeUom = unitOfMeasurement.getAsJsonObject().get("Code");
        JsonElement descriptionUom = unitOfMeasurement.getAsJsonObject().get("Description");
        JsonElement weight = billingWeight.getAsJsonObject().get("Weight");

        JsonElement packageResults = shipmentResults.getAsJsonObject().get("PackageResults");
        JsonElement TrackingNumber = packageResults.getAsJsonObject().get("TrackingNumber");

        JsonElement shippingLabel = packageResults.getAsJsonObject().get("ShippingLabel");
        JsonElement imageFormat = shippingLabel.getAsJsonObject().get("ImageFormat");
        JsonElement codeIf = imageFormat.getAsJsonObject().get("Code");
        JsonElement descriptionIf = imageFormat.getAsJsonObject().get("Description");
        JsonElement graphicImage = shippingLabel.getAsJsonObject().get("GraphicImage");
        JsonElement HTMLImage = shippingLabel.getAsJsonObject().get("HTMLImage");

        UnitOfMeasurement _UnitOfMeasurement = new UnitOfMeasurement();
        _UnitOfMeasurement.setCode(codeUom.getAsString());
        _UnitOfMeasurement.setDescription(descriptionUom.getAsString());

        BillingWeight _BillingWeight = new BillingWeight();
        _BillingWeight.setUnitOfMeasurement(_UnitOfMeasurement);
        _BillingWeight.setWeight(weight.getAsString());

        ServiceOptionsCharges _ServiceOptionsCharges = new ServiceOptionsCharges();
        _ServiceOptionsCharges.setCurrencyCode(currencyCodeSoc.getAsString());
        _ServiceOptionsCharges.setMonetaryvalue(monetaryValueSoc.getAsString());

        TransportationCharges _TransportationCharges = new TransportationCharges();
        _TransportationCharges.setCurrencyCode(currencyCode.getAsString());
        _TransportationCharges.setMonetaryvalue(monetaryValue.getAsString());

        TotalCharges _TotalCharges = new TotalCharges();
        _TotalCharges.setCurrencyCode(currencyCodeTc.getAsString());
        _TotalCharges.setMonetaryvalue(monetaryValueTc.getAsString());

        ShipmentCharges _ShipmentCharges = new ShipmentCharges();
        _ShipmentCharges.setServiceOptionsCharges(_ServiceOptionsCharges);
        _ShipmentCharges.setTransportationCharges(_TransportationCharges);
        _ShipmentCharges.setTotalCharges(_TotalCharges);

        ImageFormat _ImageFormat = new ImageFormat();
        _ImageFormat.setCode(codeIf.getAsString());
        _ImageFormat.setDescription(descriptionIf.getAsString());

        ShippingLabel _ShippingLabel = new ShippingLabel();
        _ShippingLabel.setGraphImage(graphicImage.getAsString());
        _ShippingLabel.setHTMLImage(HTMLImage.getAsString());
        _ShippingLabel.setImageFormat(_ImageFormat);

        PackageResults _PackageResults = new PackageResults();
        _PackageResults.setServiceOptionsCharges(_ServiceOptionsCharges);
        _PackageResults.setShippingLabel(_ShippingLabel);
        _PackageResults.setTrackingNumber(TrackingNumber.getAsString());

        ShipmentResults _ShipmentResults = new ShipmentResults();
        _ShipmentResults.setBllingWeight(_BillingWeight);
        _ShipmentResults.setPackageResults(_PackageResults);
        _ShipmentResults.setShipmentCharges(_ShipmentCharges);
        _ShipmentResults.setShipmentIdentificationNumber(shipmentIdentificationNumber.getAsString());

        return _ShipmentResults;

    }

    private String makePickupCreationCall(String requestBody) {
        String LabelCreationUrl = "https://wwwcie.ups.com/ship/v1607/pickups";

        WebClient client = WebClient.create(LabelCreationUrl);

        ResponseEntity<String> result = client.post().headers(httpHeaders -> {
            httpHeaders.set("Username", upsUserName);
            httpHeaders.set("Password", upsPassword);
            httpHeaders.set("AccessLicenseNumber", upsAccessKey);
        }).contentType(MediaType.APPLICATION_JSON).bodyValue(requestBody).exchange()
                .flatMap(response -> response.toEntity(String.class)).block();

        log.info("The Response Status Code = {}", result.toString());
        System.out.println("The Response Status Code" + result);

        if (result.getStatusCodeValue() == 200) {
            return result.getBody();
        } else {
            return "badResponse";
        }
    }

    @Override
    public PickupCreationResponse pickupCreation(PickupCreationRequest pickupCreationRequest) {
        log.info("createShippingLabel = {}", pickupCreationRequest);

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

        JsonElement je = gson.toJsonTree(pickupCreationRequest);
        JsonObject jo = new JsonObject();

        jo.add("PickupCreationRequest", je);
        String requestBody = jo.toString();
        System.out.println("requestBody" + requestBody);
        log.info("Request Body as JSON = {}", requestBody);
        String response = makePickupCreationCall(requestBody);

        System.out.println("response " + response);
        PickupCreationResponse pickupCreationResponse = persitsPickupResponseDataToDtos(response);

        return pickupCreationResponse;

    }

    private PickupCreationResponse persitsPickupResponseDataToDtos(String response) {
        JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);

        JsonElement pickupCreationResponse = jsonObject.get("PickupCreationResponse");

        JsonElement prn = pickupCreationResponse.getAsJsonObject().get("PRN");

        RateStatus rateStatus = popualteRateStatusDto(response);
        RateResult rateResult = popualteRateResultDto(response);
        Response responseDto = popualteResponseDtoForPickResponse(response);

        PickupCreationResponse _PickupCreationResponse = new PickupCreationResponse();

        _PickupCreationResponse.setPRN(prn.getAsString());
        _PickupCreationResponse.setRateStatus(rateStatus);
        _PickupCreationResponse.setResponse(responseDto);
        _PickupCreationResponse.setRateResult(rateResult);

        return _PickupCreationResponse;
    }

    private List<ChargeDetail> popualtelistofChargeDetaildto(String response) {
        JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);
        List<ChargeDetail> chargeDetails = new ArrayList<ChargeDetail>();
        JsonElement pickupCreationResponse = jsonObject.get("PickupCreationResponse");

        if (pickupCreationResponse.getAsJsonObject().has("RateResult")) {

            JsonElement rateResult = pickupCreationResponse.getAsJsonObject().get("RateResult");
            JsonElement chargeDetail = rateResult.getAsJsonObject().get("ChargeDetail");

            JsonArray chargeCode = chargeDetail.getAsJsonArray();

            for (int charge = 0; charge < chargeCode.size(); charge++) {

                JsonElement string = chargeCode.get(charge);

                JsonElement chargecode = string.getAsJsonObject().get("ChargeCode");
                JsonElement ChargeDescription = string.getAsJsonObject().get("ChargeDescription");

                JsonElement ChargeAmount = string.getAsJsonObject().get("ChargeAmount");

                JsonElement TaxAmount = string.getAsJsonObject().get("TaxAmount");
                ChargeDetail _ChargeDetail = new ChargeDetail();

                _ChargeDetail.setChargeCode(chargecode.getAsString());
                _ChargeDetail.setChargeAmount(ChargeAmount.getAsString());
                _ChargeDetail.setChargeDescription(ChargeDescription.getAsString());
                _ChargeDetail.setTaxAmount(TaxAmount.getAsString());

                chargeDetails.add(_ChargeDetail);
            }
        }
        return chargeDetails;

    }

    private RateResult popualteRateResultDto(String response) {
        JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);

        JsonElement pickupCreationResponse = jsonObject.get("PickupCreationResponse");

        JsonElement rateResult = pickupCreationResponse.getAsJsonObject().get("RateResult");
        JsonElement rateType = rateResult.getAsJsonObject().get("RateType");
        JsonElement currencyCode = rateResult.getAsJsonObject().get("CurrencyCode");
        JsonElement grandTotalOfAllCharge = rateResult.getAsJsonObject().get("GrandTotalOfAllCharge");

        List<ChargeDetail> chargeDetails = popualtelistofChargeDetaildto(response);

        RateResult _RateResult = new RateResult();
        _RateResult.setChargeDetails(chargeDetails);
        _RateResult.setCurrencyCode(currencyCode.getAsString());
        _RateResult.setRateType(rateType.getAsString());
        _RateResult.setGrandTotalOfAllCharge(grandTotalOfAllCharge.getAsString());

        return _RateResult;

    }

    private RateStatus popualteRateStatusDto(String response) {
        JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);

        JsonElement pickupCreationResponse = jsonObject.get("PickupCreationResponse");
        JsonElement rateStatus = pickupCreationResponse.getAsJsonObject().get("RateStatus");
        JsonElement rsCode = rateStatus.getAsJsonObject().get("Code");
        JsonElement rsDescription = rateStatus.getAsJsonObject().get("Description");

        RateStatus _RateStatus = new RateStatus();
        _RateStatus.setCode(rsCode.getAsString());
        _RateStatus.setDescription(rsDescription.getAsString());
        return _RateStatus;
    }

    private Response popualteResponseDtoForPickResponse(String response) {

        JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);

        JsonElement pickupCreationResponse = jsonObject.get("PickupCreationResponse");

        JsonElement responseElement = pickupCreationResponse.getAsJsonObject().get("Response");
        JsonElement responseStatus = responseElement.getAsJsonObject().get("ResponseStatus");
        JsonElement code = responseStatus.getAsJsonObject().get("Code");
        JsonElement description = responseStatus.getAsJsonObject().get("Description");

        ResponseStatus _ResponseStatus = new ResponseStatus();
        _ResponseStatus.setCode(code.getAsString());
        _ResponseStatus.setDescription(description.getAsString());

        Response _Response = new Response();
        _Response.setResponseStatus(_ResponseStatus);

        return _Response;

    }

    @Override
    public TrackResponse getTrackingDetails(String trackingNumber) {

        String result = makeGetTrackingResponseCall(trackingNumber);

        return null;
    }

    private String makeGetTrackingResponseCall(String trackingNumber) {

        String LabelCreationUrl = "https://wwwcie.ups.com/track/v1/details/";

        WebClient client = WebClient.create(LabelCreationUrl);

        ResponseEntity<String> result = client.get().uri(trackingNumber).headers(httpHeaders -> {
            httpHeaders.set("Username", upsUserName);
            httpHeaders.set("Password", upsPassword);
            httpHeaders.set("AccessLicenseNumber", upsAccessKey);
        }).exchange().flatMap(response -> response.toEntity(String.class)).block();

        log.info("The Response Status Code = {}", result.toString());
        System.out.println("The Response Status Code" + result);

        if (result.getStatusCodeValue() == 200) {
            return result.getBody();
        } else {
            return "badResponse";
        }

    }

}

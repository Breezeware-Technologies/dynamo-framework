package net.breezeware.dynamo.shipping.ups.service.impl;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.dynamo.shipping.ups.dto.shipping.BillingWeight;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ImageFormat;
import net.breezeware.dynamo.shipping.ups.dto.shipping.PackageResults;
import net.breezeware.dynamo.shipping.ups.dto.shipping.Response;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ResponseStatus;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ServiceOptionsCharges;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ShipmentCharges;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ShipmentRequest;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ShipmentResponse;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ShipmentResults;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ShippingLabel;
import net.breezeware.dynamo.shipping.ups.dto.shipping.TotalCharges;
import net.breezeware.dynamo.shipping.ups.dto.shipping.TransportationCharges;
import net.breezeware.dynamo.shipping.ups.dto.shipping.UnitOfMeasurement;
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

        ResponseEntity<String> result = (ResponseEntity<String>) client.post().headers(httpHeaders -> {
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

}

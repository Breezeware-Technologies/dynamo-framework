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
import net.breezeware.dynamo.shipping.ups.dto.pickup.PickupCreationRequest;
import net.breezeware.dynamo.shipping.ups.dto.pickup.PickupCreationResponse;
import net.breezeware.dynamo.shipping.ups.dto.pickup.PickupCreationResponseWrapper;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ShipmentRequest;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ShipmentResponse;
import net.breezeware.dynamo.shipping.ups.dto.shipping.ShipmentResponseWrapper;
import net.breezeware.dynamo.shipping.ups.dto.tracking.TrackResponse;
import net.breezeware.dynamo.shipping.ups.dto.tracking.TrackingResponseWrapper;
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
        log.info("Request Body as JSON = {}", requestBody);
        String response = makeLabelCreationCall(requestBody);

        ShipmentResponse shipmentResponse = populateShipmentResponse(response);

        return shipmentResponse;
    }

    private ShipmentResponse populateShipmentResponse(String response) {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

        ShipmentResponseWrapper shipmentResponseWrapper = gson.fromJson(response, ShipmentResponseWrapper.class);

        return shipmentResponseWrapper.getShipmentResponse();

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
        log.info("Request Body as JSON = {}", requestBody);
        String response = makePickupCreationCall(requestBody);

        PickupCreationResponse pickupCreationResponse = persitsPickupResponseDataToDtos(response);

        return pickupCreationResponse;

    }

    private PickupCreationResponse persitsPickupResponseDataToDtos(String response) {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

        PickupCreationResponseWrapper pickupCreationResponseWrapper = gson.fromJson(response,
                PickupCreationResponseWrapper.class);

        return pickupCreationResponseWrapper.getPickupCreationResponse();
    }

    @Override
    public TrackResponse getTrackingDetails(String trackingNumber) {

        String result = makeGetTrackingResponseCall(trackingNumber);

        TrackResponse trackResponse = populateTrackingResponseDto(result);

        return trackResponse;
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

        if (result.getStatusCodeValue() == 200) {
            return result.getBody();
        } else {
            return "badResponse";
        }

    }

    private TrackResponse populateTrackingResponseDto(String response) {

        Gson gson = new Gson();
        TrackingResponseWrapper trackingResponseWrapper = gson.fromJson(response, TrackingResponseWrapper.class);

        return trackingResponseWrapper.getTrackResponse();

    }

}

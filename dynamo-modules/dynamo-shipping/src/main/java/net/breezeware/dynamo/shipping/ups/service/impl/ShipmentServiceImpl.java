package net.breezeware.dynamo.shipping.ups.service.impl;

import org.springframework.http.HttpStatus;
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
import net.breezeware.dynamo.shipping.ups.dto.ShipmentRequest;
import net.breezeware.dynamo.shipping.ups.service.api.ShipmentService;

@Service
@Slf4j
public class ShipmentServiceImpl implements ShipmentService {

    private final String upsUserName = "refreshhealth";

    private final String upsPassword = "Refresh123!";

    private final String upsAccessKey = "1D925021583D91B2";

    private final String upsAccountNumber = "34V933";

    private String makeLabelCreationCall(String requestBody) {
        String LabelCreationUrl = "https://wwwcie.ups.com/ship/v1/shipments";
        System.out.println("HI" + LabelCreationUrl);
        System.out.println("AccessLicenseNumber" + upsAccessKey);

        WebClient client = WebClient.create(LabelCreationUrl);

        ResponseEntity<String> result = (ResponseEntity<String>) client.post().headers(httpHeaders -> {
            httpHeaders.set("Username", upsUserName);
            httpHeaders.set("Password", upsPassword);
            httpHeaders.set("AccessLicenseNumber", upsAccessKey);
        }).contentType(MediaType.APPLICATION_JSON).bodyValue(requestBody).exchange()
                .flatMap(response -> response.toEntity(String.class)).block();

        log.info("The Response Status Code = {}", result.getBody());
        System.out.println(result);

        if (result.getStatusCodeValue() == 200) {
            return result.getBody();
        } else {
            return "badResponse";
        }
    }

    public String createShippingLabel(ShipmentRequest shipmentRequest) {
        log.info("createShippingLabel = {}", shipmentRequest);
        // shipmentRequest = populateShippingLabelCreation();

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

        JsonElement je = gson.toJsonTree(shipmentRequest);
        JsonObject jo = new JsonObject();

        jo.add("ShipmentRequest", je);
        String requestBody = jo.toString();
        System.out.println(requestBody);
        log.info("Request Body as JSON = {}", requestBody);
        return makeLabelCreationCall(requestBody);
    }

}

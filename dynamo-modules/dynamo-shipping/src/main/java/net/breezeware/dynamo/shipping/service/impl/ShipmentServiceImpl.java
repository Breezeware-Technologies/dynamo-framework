package net.breezeware.dynamo.shipping.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import net.breezeware.dynamo.shipping.dto.ShipmentRequest;

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

        ResponseEntity<String> result = client.post().headers(httpHeaders -> {
            httpHeaders.set("Username", upsUserName);
            httpHeaders.set("Password", upsPassword);
            httpHeaders.set("AccessLicenseNumber", upsAccessKey);
        }).contentType(MediaType.APPLICATION_JSON).exchange().flatMap(response -> response.toEntity(String.class))
                .block();

        log.info("The Response Status Code = {}", result.getBody());
        System.out.println(result);

        if (result.getStatusCode().equals(HttpStatus.OK)) {
            return result.getBody();
        } else {
            return "badResponse";
        }
    }

    public String createShippingLabel(ShipmentRequest shipmentRequest) {
        log.info("createShippingLabel = {}", shipmentRequest);
        // shipmentRequest = populateShippingLabelCreation();

        Gson gson = new Gson();
        String requestBody = gson.toJson(shipmentRequest);
        System.out.println(requestBody);
        log.info("Request Body as JSON = {}", requestBody);
        return makeLabelCreationCall(requestBody);
    }

}

package net.breezeware.dynamo.communication.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clxcommunications.xms.ApiConnection;
import com.clxcommunications.xms.ClxApi;
import com.clxcommunications.xms.api.MtBatchTextSmsResult;

import net.breezeware.dynamo.communication.DynamoCommunicationConfigProperties;
import net.breezeware.dynamo.communication.service.api.SmsService;

@Service
public class SinchSmsService implements SmsService {
    Logger logger = LoggerFactory.getLogger(SinchSmsService.class);

    @Autowired
    DynamoCommunicationConfigProperties dynamoCommunicationConfigProperties;

    /**
     * {@inheritDoc}
     */
    public String sendSms(String message, String phoneNumber) {
        logger.info("Entering sendSms(). Message = {}, Phone number = {}, Service Plan ID = {}, API Token = {}",
                message, phoneNumber, dynamoCommunicationConfigProperties.getSinchServicePlanId(),
                dynamoCommunicationConfigProperties.getSinchApiToken());

        try {
            ApiConnection conn = ApiConnection.builder()
                    .servicePlanId(dynamoCommunicationConfigProperties.getSinchServicePlanId())
                    .token(dynamoCommunicationConfigProperties.getSinchApiToken()).start();

            MtBatchTextSmsResult result = conn
                    .createBatch(ClxApi.batchTextSms().sender("12345").addRecipient(phoneNumber).body(message).build());
            return result.body();
        } catch (Exception e) {
            logger.error("Error SMS " + e);
            return "Error " + e;
        }
    }
}
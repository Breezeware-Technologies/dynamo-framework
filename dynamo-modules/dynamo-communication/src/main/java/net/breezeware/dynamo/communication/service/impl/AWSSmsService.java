package net.breezeware.dynamo.communication.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

import net.breezeware.dynamo.communication.DynamoCommunicationConfigProperties;
import net.breezeware.dynamo.communication.service.api.SmsService;

@Service
public class AWSSmsService implements SmsService {
	Logger logger = LoggerFactory.getLogger(AWSSmsService.class);

	@Autowired
	DynamoCommunicationConfigProperties dynamoCommunicationConfigProperties;

	public String sendSms(String message, String phoneNumber) {
		logger.info("Entering sendSms(). Message = {}, Phone number = {}", message, phoneNumber);

		try {
			Map<String, MessageAttributeValue> smsAttributes = new HashMap<String, MessageAttributeValue>();
			smsAttributes.put("AWS.SNS.SMS.SenderID",
					new MessageAttributeValue().withStringValue("tsgSecurity").withDataType("String"));
			smsAttributes.put("AWS.SNS.SMS.MaxPrice",
					new MessageAttributeValue().withStringValue("0.50").withDataType("Number"));
			smsAttributes.put("AWS.SNS.SMS.SMSType",
					new MessageAttributeValue().withStringValue("Transactional").withDataType("String"));

			// AmazonSNSClient snsClient = new AmazonSNSClient();

			AmazonSNSClient snsClient = new AmazonSNSClient(
					new BasicAWSCredentials(dynamoCommunicationConfigProperties.getAwsSnsAccesskey(),
							dynamoCommunicationConfigProperties.getAwsSnsSecretkey()));
			snsClient.setRegion(
					Region.getRegion(Regions.fromName(dynamoCommunicationConfigProperties.getAwsSnsRegion())));

			String messageId = sendSMSMessage(snsClient, message, phoneNumber, smsAttributes);

			logger.info("Sent message with message id: {}", messageId);
			return messageId;
		} catch (Exception e) {
			logger.error("Error SMS " + e);
			return "Error " + e;
		}
	}

	private String sendSMSMessage(AmazonSNSClient snsClient, String message, String phoneNumber,
			Map<String, MessageAttributeValue> smsAttributes) {
		PublishResult result = snsClient.publish(new PublishRequest().withMessage(message).withPhoneNumber(phoneNumber)
				.withMessageAttributes(smsAttributes));

		return result.getMessageId();
	}

}
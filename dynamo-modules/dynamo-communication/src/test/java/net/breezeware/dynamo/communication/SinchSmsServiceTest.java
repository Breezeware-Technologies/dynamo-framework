package net.breezeware.dynamo.communication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;

import net.breezeware.dynamo.communication.service.impl.SinchSmsService;

@ContextConfiguration(classes = TestApplication.class)
@WebAppConfiguration
public class SinchSmsServiceTest extends AbstractTestNGSpringContextTests {

	Logger logger = LoggerFactory.getLogger(SinchSmsServiceTest.class);

	@Autowired
	SinchSmsService sinchSmsService;

	@Test
	public void sendOtpToUniqueUserIdTest() {
		logger.info("Entering sendOtpToUniqueUserIdTest()");

		String message = "Tower Security Group - Security Code is 123456";
		String phoneNumber = "919677122547";

		logger.info("Sending message '{}' to phone '{}'", message, phoneNumber);

		String messageId = sinchSmsService.sendSms(message, phoneNumber);
		logger.info("Send message ID = {}", messageId);

		logger.info("Entering sendOtpToUniqueUserIdTest()");
	}
}
package net.breezeware.dynamo.communication.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.breezeware.dynamo.communication.service.api.SmsService;

@Service
public class TextLocalSmsService implements SmsService {
    Logger logger = LoggerFactory.getLogger(TextLocalSmsService.class);

    /**
     * {@inheritDoc}
     */
    public String sendSms(String message, String phoneNumber) {
        logger.info("Entering sendSms(). Message = {}, Phone number = {}", message, phoneNumber);

        try {
            // Construct data
            String apiKey = "apikey=" + "lQjdOgyoVgg-CCR7DI6UIQ788Am4Qc4Tw1agZDqdZO";
            message = "&message=" + message;
            String sender = "&sender=" + "TXTLCL";
            String numbers = "&numbers=" + phoneNumber;

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

            logger.info("SMS send response = {}", stringBuffer.toString());

            logger.info("Leaving sendSms()");
            return stringBuffer.toString();
        } catch (Exception e) {
            logger.error("Error SMS " + e);
            return "Error " + e;
        }
    }
}
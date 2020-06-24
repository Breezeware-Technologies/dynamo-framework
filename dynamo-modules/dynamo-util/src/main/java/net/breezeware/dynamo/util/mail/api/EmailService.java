package net.breezeware.dynamo.util.mail.api;

import java.util.Map;

/**
 * Interface used to manage email services like sending and receiving.
 * 
 */
public interface EmailService {

    /**
     * Sends an email with a subject and body.
     * 
     * @param from
     *            Sender's email address
     * @param to
     *            Receiver's email address
     * @param subject
     *            A brief summary of the message
     * @param keyVals
     *            Map of string keys and their string values. These keys are
     *            included in the message template (identified by the template
     *            name) as place holders that will eventually be replaced by
     *            their respective values.
     * @param templateName
     *            Name of the Thymeleaf template used to compose the message
     * */
    void sendMail(String from, String to, String subject, Map<String, String> keyVals, String templateName);
}
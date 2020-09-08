package net.breezeware.dynamo.communication.service.api;

public interface SmsService {
    /**
     * Send an SMS message to the provided phone number.
     * @param message     the message to be sent
     * @param phoneNumber the phone number to which the message will be sent
     * @return the String value returned from the service provider that is used to
     *         send the message
     */
    String sendSms(String message, String phoneNumber);
}
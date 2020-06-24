package net.breezeware.dynamo.communication.service.api;

public interface SmsService {
	String sendSms(String message, String phoneNumber);
}
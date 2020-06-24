package net.breezeware.dynamo.communication;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

/**
 * Bean to hold Dynamo Communication Module's configuration properties.
 * 
 * @author karthik
 *
 */

@Configuration
@ConfigurationProperties(prefix = "dynamo-communication")
@RefreshScope
public class DynamoCommunicationConfigProperties {

	/**
	 * Amazon Web Services (AWS) SNS (Simple Notification Service) credentials.
	 */
	private String awsSnsAccesskey;
	private String awsSnsSecretkey;
	private String awsSnsRegion;

	/**
	 * Sinch Messaging Service credentials.
	 */
	private String sinchApiToken;
	private String sinchServicePlanId;

	public String getAwsSnsAccesskey() {
		return awsSnsAccesskey;
	}

	public void setAwsSnsAccesskey(String awsSnsAccesskey) {
		this.awsSnsAccesskey = awsSnsAccesskey;
	}

	public String getAwsSnsSecretkey() {
		return awsSnsSecretkey;
	}

	public void setAwsSnsSecretkey(String awsSnsSecretkey) {
		this.awsSnsSecretkey = awsSnsSecretkey;
	}

	public String getAwsSnsRegion() {
		return awsSnsRegion;
	}

	public void setAwsSnsRegion(String awsSnsRegion) {
		this.awsSnsRegion = awsSnsRegion;
	}

	public String getSinchApiToken() {
		return sinchApiToken;
	}

	public void setSinchApiToken(String sinchApiToken) {
		this.sinchApiToken = sinchApiToken;
	}

	public String getSinchServicePlanId() {
		return sinchServicePlanId;
	}

	public void setSinchServicePlanId(String sinchServicePlanId) {
		this.sinchServicePlanId = sinchServicePlanId;
	}

	public String toString() {
		return new Gson().toJson(this);
	}
}
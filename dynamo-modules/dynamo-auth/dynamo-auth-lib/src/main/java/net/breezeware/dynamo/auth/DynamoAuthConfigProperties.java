package net.breezeware.dynamo.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

/**
 * Bean to hold Dynamo Audit Module's configuration properties.
 * @author karthik
 */

@Configuration
@ConfigurationProperties(prefix = "dynamo-auth")
@RefreshScope
public class DynamoAuthConfigProperties {

    private String passwordEncoding;

    public String getPasswordEncoding() {
        return passwordEncoding;
    }

    public void setPasswordEncoding(String passwordEncoding) {
        this.passwordEncoding = passwordEncoding;
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
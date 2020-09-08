package net.breezeware.dynamo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

/**
 * Bean to hold mandatory application configuration properties. This bean is
 * annotated with '@Configuration' to be picked up during component scan.
 * Annotation @RefreshScope enables this bean to be refreshed by 'Spring Cloud
 * Config' mechanisms so that bean properties are refreshed with application
 * restart.
 * @author karthik
 */

@Configuration
@ConfigurationProperties(prefix = "dynamo")
@RefreshScope
public class DynamoConfigProperties {

    /**
     * Flag to denote whether logging is enabled or disabled. The key for this
     * property in the DB is 'enableLogging'. Logging is enabled if value is true,
     * disabled otherwise.
     */
    private boolean enableLogging;

    private String applicationName;
    private String applicationOwner;
    private String applicationCopyrightYear;
    private String applicationAdminEmail;
    private String applicationServerUrl;

    public boolean isEnableLogging() {
        return enableLogging;
    }

    public void setEnableLogging(boolean enableLogging) {
        this.enableLogging = enableLogging;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationOwner() {
        return applicationOwner;
    }

    public void setApplicationOwner(String applicationOwner) {
        this.applicationOwner = applicationOwner;
    }

    public String getApplicationCopyrightYear() {
        return applicationCopyrightYear;
    }

    public void setApplicationCopyrightYear(String applicationCopyrightYear) {
        this.applicationCopyrightYear = applicationCopyrightYear;
    }

    public String getApplicationAdminEmail() {
        return applicationAdminEmail;
    }

    public void setApplicationAdminEmail(String applicationAdminEmail) {
        this.applicationAdminEmail = applicationAdminEmail;
    }

    public String getApplicationServerUrl() {
        return applicationServerUrl;
    }

    public void setApplicationServerUrl(String applicationServerUrl) {
        this.applicationServerUrl = applicationServerUrl;
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
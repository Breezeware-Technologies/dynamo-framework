package net.breezeware.dynamo.audit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

/**
 * Bean to hold Dynamo Audit Module's configuration properties.
 * @author karthik
 */

@Configuration
@ConfigurationProperties(prefix = "dynamo-audit")
@RefreshScope
public class DynamoAuditConfigProperties {

    /**
     * Flag to denote whether auditing is enabled or disabled. The key for this
     * property in the DB is 'enableAuditing'. Auditing is enabled if value is true,
     * disabled otherwise.
     */
    private boolean enableAuditing;

    public boolean isEnableAuditing() {
        return enableAuditing;
    }

    public void setEnableAuditing(boolean enableAuditing) {
        this.enableAuditing = enableAuditing;
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
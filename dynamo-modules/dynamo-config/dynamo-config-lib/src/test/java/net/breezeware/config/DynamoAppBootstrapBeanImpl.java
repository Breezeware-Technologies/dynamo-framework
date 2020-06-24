package net.breezeware.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttributes;

import net.breezeware.dynamo.config.DynamoConfigProperties;
import net.breezeware.dynamo.util.DynamoAppBootstrapBean;

@Component
@RefreshScope
@SessionAttributes("currentUser")
public class DynamoAppBootstrapBeanImpl implements DynamoAppBootstrapBean {

    Logger logger = LoggerFactory.getLogger(DynamoAppBootstrapBeanImpl.class);

    @Autowired
    DynamoConfigProperties dynamoConfigProperties;

    // @Autowired
    // private ContextRefresher refresher;

    public long getCurrentOrganizationId() {
        // FIXME:
        return 1;
    }

    public String getCurrentUsername() {
        // FIXME:

        return "karthik";
    }

    public boolean isAuditingEnabled() {

        // refresher.refresh();

        logger.info("Entering isAuditingEnabled()");
        boolean isEnabled = dynamoConfigProperties.isEnableAuditing();

        logger.info("Leaving isAuditingEnabled(). isEnabled = {}", isEnabled);
        return isEnabled;
    }
}
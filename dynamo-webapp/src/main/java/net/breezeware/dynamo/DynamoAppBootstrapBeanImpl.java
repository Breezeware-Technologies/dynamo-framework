package net.breezeware.dynamo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import net.breezeware.dynamo.auth.springsecurity.SecurityContextUtils;
import net.breezeware.dynamo.util.DynamoAppBootstrapBean;

@Component
@RefreshScope
public class DynamoAppBootstrapBeanImpl implements DynamoAppBootstrapBean {

	Logger logger = LoggerFactory.getLogger(DynamoAppBootstrapBeanImpl.class);

	public String getCurrentUserEmail() {
		logger.info("Entering getCurrentUserEmail()");

		String securityContextUsername = SecurityContextUtils.getUserIdFromSecurityContext();
		logger.info("securityContextUsername = {}", securityContextUsername);

		logger.info("Leaving getCurrentUserEmail(). Username = {}", securityContextUsername);
		return securityContextUsername;
	}

	public List<String> getCurrentUserRoles() {
		logger.info("Entering getCurrentUserRoles()");
		List<String> roles = SecurityContextUtils.getUserRolesFromSecurityContext();

		logger.info("Leaving getCurrentUserRoles(). # of roles = {}", roles.size());
		return roles;
	}

	public long getCurrentUserOrganizationId() {
		long orgId = SecurityContextUtils.getUserOrganizationIdFromSecurityContext();
		logger.info("Organization Id = {}", orgId);

		return orgId;
	}
}
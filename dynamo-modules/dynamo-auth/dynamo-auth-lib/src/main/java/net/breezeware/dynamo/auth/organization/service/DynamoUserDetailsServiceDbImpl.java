package net.breezeware.dynamo.auth.organization.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.breezeware.dynamo.audit.aspectj.Auditable;
import net.breezeware.dynamo.auth.springsecurity.DynamoUserDetailsService;
import net.breezeware.dynamo.organization.entity.User;
import net.breezeware.dynamo.organization.entity.UserRoleMap;
import net.breezeware.dynamo.organization.service.api.OrganizationService;

/**
 * Service to create a Spring Security UserDetails object from Dynamo's Database
 * authentication tables.
 */
@Service
@Profile("DBAuthentication")
public class DynamoUserDetailsServiceDbImpl implements DynamoUserDetailsService {

    Logger logger = LoggerFactory.getLogger(DynamoUserDetailsServiceDbImpl.class);

    @Value("${dynamo.userAuthenticationField}")
    private String userAuthenticationField;

    @Autowired
    OrganizationService organizationService;

    @Auditable(event = "Retrieve User by Username", argNames = "username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Entering loadUserByUsername(). username = '{}'.", username);

        // Dynamo User entity retrieved from DB
        User user = null;

        // Username (either email or uniqueUserId) used to validate the user
        String authenticatedUsername = "";

        if (userAuthenticationField.equalsIgnoreCase("uniqueUserId")) {
            user = organizationService.findByUniqueUserIdIgnoreCase(username);
            if (user != null) {
                authenticatedUsername = user.getUserUniqueId();
            }
        } else if (userAuthenticationField.equalsIgnoreCase("email")) {
            user = organizationService.findByEmailIgnoreCase(username);
            if (user != null) {
                authenticatedUsername = user.getEmail();
            }
        } else if (userAuthenticationField.equalsIgnoreCase("uniqueUserIdOrEmail")) {
            user = organizationService.findByEmailIgnoreCase(username);
            if (user == null) {
                user = organizationService.findByUniqueUserIdIgnoreCase(username);
            } else {
                authenticatedUsername = user.getEmail();
            }

            if (authenticatedUsername.trim().length() <= 0 && user != null) {
                authenticatedUsername = user.getUserUniqueId();
            }
        }

        logger.info("Authenticated Username = {}", authenticatedUsername);

        if (user == null) {
            throw new UsernameNotFoundException("Username: " + username + " not found in the system.");
        } else if (!user.getStatus().equalsIgnoreCase(User.STATUS_ACTIVE)) {
            logger.info("User is not active. Status is {}", user.getStatus());
            throw new UsernameNotFoundException("User is not Active. Could not Login");
        } else {
            logger.info("User object found. User ID = '{}'. Username = '{}'.", user.getId(), user.getEmail());
        }

        // create a list of Spring GrantedAuthority objects from roles defined
        // for the Dynamo User.
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        for (UserRoleMap userRole : user.getUserRoleMap()) {
            if (userRole.getRole() != null) {
                logger.debug("Adding role '{}' in DB to Authorities.", userRole.getRole().getName());
                authorities.add(new SimpleGrantedAuthority(userRole.getRole().getName()));
            }
        }

        logger.debug("Proceeding to create a Spring Security User");

        DynamoSpringUser dynamoSpringUser = null;
        dynamoSpringUser = new DynamoSpringUser(authenticatedUsername, user.getPassword(), true, true, true, true,
                authorities);
        dynamoSpringUser.setOrganizationId(user.getOrganization().getId());

        logger.debug("Spring User built from Dynamo User. Spring user details: Name = '{}'. Password = '{}'",
                dynamoSpringUser.getUsername(), dynamoSpringUser.getPassword());
        logger.info("Spring User built from Dynamo User. Spring user details: # of authorities = '{}'.",
                dynamoSpringUser.getAuthorities().size());

        logger.info("Leaving loadUserByUsername().");
        return dynamoSpringUser;
    }
}
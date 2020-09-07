package net.breezeware.dynamo.auth.springsecurity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import net.breezeware.dynamo.auth.organization.service.DynamoSpringUser;

public class SecurityContextUtils {
    /**
     * Retrieves the user Id of the currently logged in user.
     * @return userId Logged-in User ID
     */
    public static String getUserIdFromSecurityContext() {
        String userId = "";
        // principal
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {
            userId = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return userId;
    }

    /**
     * Retrieve a list of roles associated with the user currently logged into the
     * application.
     * @return a list of user roles for the current user
     */
    public static List<String> getUserRolesFromSecurityContext() {
        List<String> roles = new ArrayList<String>();
        // principal
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null
                && SecurityContextHolder.getContext().getAuthentication().getAuthorities() != null) {

            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
                    .getAuthorities();
            for (GrantedAuthority ga : authorities) {
                roles.add(ga.getAuthority());
            }
        }

        return roles;
    }

    /**
     * Retrieve the organization ID for the currently logged-in user.
     * @return the organization ID.
     */
    public static long getUserOrganizationIdFromSecurityContext() {

        long defaultOrganizationId = 1;

        // principal
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null
                && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof DynamoSpringUser) {
            DynamoSpringUser dynamoSpringUser = (DynamoSpringUser) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            return (dynamoSpringUser.getOrganizationId());
        } else {
            return defaultOrganizationId;
        }
    }
}
package net.breezeware.dynamo.organization.util;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import net.breezeware.dynamo.util.usermgmt.CurrentUserDto;

@Component
public class OrganizationManagementUtil {
    /**
     * Retrieve the organization ID of the currently logged-in user from HTTP
     * session.
     * 
     * @param session
     * @return
     */
    public long getOrganizationIdFromSession(HttpSession session) {
        // get the currentUser from the session to retrieve the organizationId from it.
        CurrentUserDto currentUser = (CurrentUserDto) session.getAttribute("currentUser");
        long organizationId = -1;

        // NOTE:
        // if currentUser in session is null, use the 'defaultOrganization' in DB (i.e,
        // organizationId = 1)
        if (currentUser == null) {
            organizationId = 1;
        } else {
            organizationId = Long.valueOf(currentUser.getOrganizationId());
        }

        return organizationId;
    }
}
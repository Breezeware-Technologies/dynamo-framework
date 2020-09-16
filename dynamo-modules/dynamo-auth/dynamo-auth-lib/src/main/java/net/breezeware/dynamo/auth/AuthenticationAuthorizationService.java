package net.breezeware.dynamo.auth;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import net.breezeware.dynamo.apps.entity.DynamoApp;
import net.breezeware.dynamo.auth.organization.entity.OrganizationApiKeyMap;
import net.breezeware.dynamo.auth.organization.entity.OrganizationAppMap;
import net.breezeware.dynamo.auth.organization.entity.RoleAppMap;
import net.breezeware.dynamo.auth.organization.entity.UserAppMap;
import net.breezeware.dynamo.util.usermgmt.CurrentUserDto;

/**
 * Interface to interact with various entities in the Authentication and
 * Authorization module. Generally it is used to retrieve items that are common
 * to both Database and LDAP authentication and authorization sources.
 */
public interface AuthenticationAuthorizationService {

    /**
     * Retrieve information about the current user.
     * @param userId User Id to retrieve a user. It can be the uniqueUserId field or
     *               the email based on user preferences.
     * @return CurrentUserDto CurrentUserDto if available, else null.
     */
    CurrentUserDto getCurrentUserDto(String userId);

    /**
     * Retrieve the list of Dynamo applications subscribed by this organization.
     * @param organizationId ID assigned to an organization .
     * @return List of apps if any available, else empty list.
     */
    List<DynamoApp> getAppsSubscribedByOrganization(String organizationId);

    /**
     * Determine whether an app is already subscribed by an organization.
     * @param organizationId ID assigned to an organization .
     * @param appId          ID assigned to an app .
     * @return true is already subscribed, else false
     */
    boolean isAppSubscribedByOrganization(String organizationId, String appId);

    /**
     * Retrieve the list of Dynamo applications that are not yet subscribed by the
     * organization but those that are available for subscription.
     * @param organizationId ID assigned to an organization .
     * @return List of apps if any available, else empty list.
     */
    List<DynamoApp> getAppsAvailableForSubscriptionForOrganization(String organizationId);

    /**
     * Retrieve the list of Dynamo applications authorized for the role.
     * @param roleId ID assigned to the role in a DB to uniquely identify it.
     * @return List of apps if any available, else empty list.
     */
    List<DynamoApp> getAppsForRole(String roleId);

    /**
     * Retrieve a single app identified by its ID for a given role.
     * @param appId  ID assigned to the app to uniquely identify it.
     * @param roleId ID assigned to the role to uniquely identify it.
     * @return Single app if available, else null.
     */
    DynamoApp getSingleAppForRole(String appId, String roleId);

    /**
     * Retrieve the list of Dynamo applications authorized for the User.
     * @param email Email to uniquely identify a user.
     * @return List of apps if any available, else empty list.
     */
    List<DynamoApp> getAppsForUser(String email);

    /**
     * Save the list of apps to the organization.The duplicate mappings are omitted
     * by cross checking.
     * @param organizationId ID assigned to the organization to uniquely identify
     *                       it.
     * @param appIds         List of IDs for apps.
     * @return List of saved mappings.
     */

    List<OrganizationAppMap> saveOrganizationAppMap(String organizationId, List<String> appIds);

    /**
     * Retrieve the list of RoleAppMap items that are associated with a given app.
     * @param organizationId ID of the user organization
     * @param appId          ID of the app
     * @return List of RoleAppMap if available, else empty list.
     */
    List<RoleAppMap> getRoleAppMap(String organizationId, String appId);

    /**
     * Set the mappings between an app and all the roles within an organization that
     * can access the app. All the current mappings in the DB between an app and
     * roles will be overwritten.
     * @param organizationId Id of the user organization
     * @param appId          Id of the app
     * @param roleIds        List of roles that have access to this app.
     * @return Updated list of app role mappings
     */
    List<RoleAppMap> resetRoleAppMap(String organizationId, String appId, List<String> roleIds);

    /**
     * Save the list of apps to the role.The duplicate mappings are omitted by cross
     * checking.
     * @param roleId ID assigned to the role to uniquely identify it.
     * @param appIds List of IDs for apps.
     * @return List of saved mappings.
     */
    List<RoleAppMap> saveRoleAppMap(String roleId, List<String> appIds);

    /**
     * Save the list of apps to the user entity.The duplicate mappings are omitted
     * by cross checking.
     * @param userId ID assigned to the user to uniquely identify it.
     * @param appIds List of IDs for apps.
     * @return List of saved mappings.
     */
    List<UserAppMap> saveUserAppMap(String userId, List<String> appIds);

    /**
     * Remove the organization-app mapping.
     * @param organizationId ID assigned to the organization to uniquely identify
     *                       it.
     * @param appId          ID assigned to the app to uniquely identify it.
     * @return Returns true if removed else false.
     */
    boolean removeOrganizationAppMap(String organizationId, String appId);

    /**
     * Remove the role-app mapping.
     * @param roleId ID assigned to the role to uniquely identify it.
     * @param appId  ID assigned to the app to uniquely identify it.
     * @return Returns true if removed else false.
     */
    boolean removeRoleAppMap(String roleId, String appId);

    /**
     * Remove the user-app mapping.
     * @param userId ID assigned to the user to uniquely identify it.
     * @param appId  ID assigned to the app to uniquely identify it.
     * @return Returns true if removed else false.
     */
    boolean removeUserAppMap(String userId, String appId);

    /**
     * Create a new ApiKey for an Organization.
     * @param organizationId the organization for which the key will be created
     * @return OrganizationApiKeyMap created for the organization
     */
    OrganizationApiKeyMap createOrganizationApiKey(long organizationId);

    /**
     * Retrieve an OrganizationApiKeyMap by ApiKey value.
     * @param apiKeyValue the key value associated with the organization
     * @return a non-null OrganizationApiKeyMap if available, else an empty Optional
     */
    Optional<OrganizationApiKeyMap> retrieveOrganizationApiKeyByKeyValue(UUID apiKeyValue);

    /**
     * Retrieve one of more OrganizationApiKeyMap for an organization.
     * @param organizationId the organization for which the API keys are retrieved
     * @return the list of {@link OrganizationApiKeyMap}
     */
    Optional<List<OrganizationApiKeyMap>> retrieveOrganizationApiKeysForOrganization(long organizationId);

}
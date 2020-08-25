package net.breezeware.dynamo.organization.service.api;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Predicate;

import net.breezeware.dynamo.organization.dto.CreateOrganizationDto;
import net.breezeware.dynamo.organization.entity.Group;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.PasswordResetToken;
import net.breezeware.dynamo.organization.entity.Role;
import net.breezeware.dynamo.organization.entity.User;
import net.breezeware.dynamo.organization.entity.UserGroupMap;
import net.breezeware.dynamo.organization.entity.UserRegistrationToken;
import net.breezeware.dynamo.organization.entity.UserRoleMap;
import net.breezeware.dynamo.util.exeption.DynamoDataAccessException;

/**
 * Interface to manage aspects like roles,groups,organizations and users.
 * @author gowtham
 */

public interface OrganizationService {

    /**
     * Retrieves all organizations.
     * @return List of organizations.
     */
    List<Organization> findAllOrganizations();

    /**
     * Retrives all users.
     * @return List of organizations.
     */
    List<User> findAllUsers();

    /**
     * Retrieves all users with the organizationId.
     * @param organizationId Key assigned by the user to identify the list of users.
     * @return List of users.
     */
    List<User> findUsers(long organizationId);

    /**
     * Retrieves all users associated with the role.
     * @param role String given by the user to identify the list of users.
     * @return List of users.
     */
    List<User> findUsers(String role);

    /**
     * Retrieves all users having combination of both the organizationId and the
     * role.
     * @param organizationId Key assigned by the user.
     * @param role           String given by the user.
     * @return List of users.
     */
    List<User> findUsers(long organizationId, String role);

    /**
     * Retrieves an organization by its ID
     * @param organizationId
     * @return Organization
     */
    Organization findOrganizationById(long organizationId);

    /**
     * Retrieves an organization by its name
     * @param name Unique name assigned to an organization
     * @return Organization
     */
    Organization findOrganizationByName(String name);

    /**
     * Retrieves the user having the particular email.
     * @param email Email of the user.
     * @return User instance.
     */
    User findByEmailIgnoreCase(String email);

    /**
     * Retrieves the user having the specified Unique User Id.
     * @param uniqueUserId Unique user ID of the user.
     * @return User instance.
     */
    User findByUniqueUserIdIgnoreCase(String uniqueUserId);

    /**
     * Gets the password for the user identified by email
     * @param email Email to uniquely identify a user
     * @return
     */
    String findPasswordByEmail(String email);

    /**
     * Gets the password for the user identified by user ID
     * @param userId User ID to uniquely identify a user
     * @return
     */
    String findPasswordByUserId(long userId);

    /**
     * Retrieves all roles.
     * @return List of roles.
     */
    // List<Role> findAllRoles();

    /**
     * Retrieves all the roles defined for an organization.
     * @return List of roles.
     */
    List<Role> getRolesInOrganization(long organizationId);

    // /**
    // * Retrieves all roles for a single user
    // *
    // * @param userId User defined unique ID of a user
    // * @return List of roles if found, else empty list.
    // */
    // List<Role> findUserRoles(String userId);

    /**
     * retrieves all groups.
     * @return List of Groups.
     */
    List<Group> findAllGroups();

    /**
     * Retrieves all groups taking into consideration all the sort, page and filter
     * criteria.
     * @param organizationId Organization for which the groups are defined.
     * @param predicate      Filtering criteria.
     * @param pageable       Paging and Sorting criteria.
     * @return Page of groups.
     */
    Page<Group> getGroups(long organizationId, Predicate predicate, Pageable pageable);

    /**
     * Retrieve all the groups defined for an organization.
     * @param organizationId
     * @return
     */
    List<Group> getGroupsInOrganization(long organizationId);

    /**
     * Retrieves all roles taking into consideration all the sort, page and filter
     * criteria.
     * @param organizationId Organization for which the groups are defined.
     * @param predicate      Filtering criteria.
     * @param pageable       Paging and Sorting criteria.
     * @return Page of roles.
     */
    Page<Role> getRoles(long organizationId, Predicate predicate, Pageable pageable);

    /**
     * Retrieves all users across all organizations taking into consideration all
     * the sort, page and filter criteria.
     * @param predicate Filtering criteria.
     * @param pageable  Paging and Sorting criteria.
     * @return Page of users.
     */
    Page<User> getUsers(Predicate predicate, Pageable pageable);

    /**
     * Retrieves all users in an organization taking into consideration all the
     * sort, page and filter criteria.
     * @param organizationId ID representing the organization whose users will be
     *                       retrieved.
     * @param predicate      Filtering criteria.
     * @param pageable       Paging and Sorting criteria.
     * @return Page of users.
     */
    Page<User> getUsers(long organizationId, Predicate predicate, Pageable pageable);

    /**
     * Retrieves all organizations taking into consideration all the sort, page and
     * filter criteria.
     * @param predicate Filtering criteria.
     * @param pageable  Paging and Sorting criteria.
     * @return Page of organizations.
     */
    Page<Organization> getOrganizations(Predicate predicate, Pageable pageable);

    /**
     * Saves a group. Used for both create and update.
     * @param group Group instance.
     * @return Group.
     * @throws DynamoDataAccessException when groupId or name is already existing in
     *                                   database.
     */
    Group saveGroup(Group group) throws DynamoDataAccessException;

    /**
     * Saves a role.Used for both create and update.
     * @param role Role instance.
     * @return Role.
     * @throws DynamoDataAccessException when roleId or name is already existing in
     *                                   database.
     */
    Role saveRole(Role role) throws DynamoDataAccessException;

    /**
     * Edits an existing group.
     * @param group Group instance.
     * @return Edited group.
     */
    Group editGroup(Group group);

    /**
     * Finds a group by its id.
     * @param groupId User given id,readable format.
     * @return Group.
     */
    Group findGroup(long groupId);

    /**
     * Finds a role by its id.
     * @param roleId User given id,readable format.
     * @return Role.
     */
    Role findRole(long roleId);

    /**
     * Returns a Role with a given name in the organization identified by its ID.
     * @param organizationId ID of the organization to which the role belongs to
     * @param roleName       Name of the role to be found
     * @return Role if a role with the name is found in the organization, Null
     */
    Optional<Role> findRoleByName(long organizationId, String roleName);

    /**
     * Saves a user. Used for both create and edit.
     * @param user
     * @param encodePassword
     * @return
     * @throws DynamoDataAccessException
     */
    User saveUser(User user, boolean encodePassword) throws DynamoDataAccessException;

    /**
     * Creates a new user with specified roles and groups. User password will be
     * encoded before it is persisted based on the application setting.
     * @param user
     * @param encodePassword
     * @return
     * @throws DynamoDataAccessException
     */
    User createUser(User user) throws DynamoDataAccessException;

    /**
     * Saves an organization.
     * @param organization Organization instance.
     * @return Organization.
     */
    Organization saveOrganization(Organization organization) throws DynamoDataAccessException;

    /**
     * Creates a new organization and a default user with ORGANIZATION_ADMIN role in
     * it. An email is sent to the newly created user's email address upon
     * successful creation.
     * @param createOrganizationDto
     * @return
     * @throws DynamoDataAccessException
     */
    Organization createOrganizationWithDefaultUser(CreateOrganizationDto createOrganizationDto)
            throws DynamoDataAccessException;

    /**
     * Finds the groups by their respective ids.
     * @param ids
     * @return List of Groups.
     */
    List<Group> findMultipleGroups(List<Long> ids);

    /**
     * Finds the roles by their respective ids.
     * @param ids
     * @return List of Roles.
     */
    List<Role> findMultipleRoles(List<Long> ids);

    /**
     * Saves a Usergroupmap to the db.
     * @param userGroupMap UserGroupMap instance.
     * @return UserGroupMap.
     */
    UserGroupMap saveUserGroupMap(UserGroupMap userGroupMap);

    /**
     * Saves a Userrolemap to the db.
     * @param userRoleMap UserRoleMap instance.
     * @return UserGroupMap.
     */
    UserRoleMap saveUserRoleMap(UserRoleMap userRoleMap);

    /**
     * Creates and persists a user registration token for a User.
     * @param user User for whom the token is created.
     * @return Token if successfully created and persisted in the DB, else null.
     */
    UserRegistrationToken createUserRegistrationToken(User user);

    /**
     * Retrieves a user registration token for a single user by its token value
     * string.
     * @param token Token string assigned to the user registration
     * @return Token if found, else null.
     */
    UserRegistrationToken getUserRegistrationToken(String token);

    /**
     * Retrieves a user by its email.
     * @param email Email assigned to uniquely identify a user.
     * @return User if found, else null.
     */
    User getUserByEmail(String email);

    /**
     * Retrieves a user by his unique user ID
     * @param uniqueUserId Unique user ID assigned to uniquely identify a user.
     * @return User if found, else null.
     */
    User getUserByUserUniqueId(String userUniqueId);

    /**
     * Retrieves a user by its ID.
     * @param userId ID to uniquely identify a user.
     * @return User if found, else null.
     */
    User getUserById(long userId);

    /**
     * Deletes a usergroupmap instance.
     * @param userGroupMap usergroupmap instance.
     */
    void deleteUserGroupMap(UserGroupMap userGroupMap);

    /**
     * Deletes a userrolemap instance.
     * @param userRoleMap userrolemap instance.
     */
    void deleteUserRoleMap(UserRoleMap userRoleMap);

    /**
     * Creates and persists a password reset token for a User.
     * @param user User for whom the token is created.
     * @return Token if successfully created and persisted in the DB, else null.
     */
    PasswordResetToken createPasswordResetToken(User user);

    /**
     * Retrieves a password reset token for a single user by its token value string.
     * @param token Token string assigned to user for resetting password
     * @return Token if found, else null.
     */
    PasswordResetToken getPasswordResetToken(String token);

    /**
     * Retrieve a list of users in an organization who will have at least one of the
     * roles provided.
     * @param organizationId ID of the Organization to which the user belongs to
     * @param roles          List of roles among which a user may have at least one
     * @return List of users
     */
    List<User> retrieveUsersByRole(long organizationId, List<String> roles);

}
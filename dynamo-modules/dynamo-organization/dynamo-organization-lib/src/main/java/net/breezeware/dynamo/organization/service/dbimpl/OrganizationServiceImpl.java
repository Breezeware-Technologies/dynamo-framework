package net.breezeware.dynamo.organization.service.dbimpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

// import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// import org.springframework.web.context.request.RequestContextHolder;
// import org.springframework.web.context.request.ServletRequestAttributes;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.PredicateOperation;
import com.querydsl.core.types.dsl.BooleanOperation;

import net.breezeware.dynamo.audit.aspectj.Auditable;
import net.breezeware.dynamo.organization.dao.AddressRepository;
import net.breezeware.dynamo.organization.dao.GroupRepository;
import net.breezeware.dynamo.organization.dao.OrganizationRepository;
import net.breezeware.dynamo.organization.dao.PasswordResetTokenRepository;
import net.breezeware.dynamo.organization.dao.RoleRepository;
import net.breezeware.dynamo.organization.dao.UserGroupMapRepository;
import net.breezeware.dynamo.organization.dao.UserRegistrationTokenRepository;
import net.breezeware.dynamo.organization.dao.UserRepository;
import net.breezeware.dynamo.organization.dao.UserRoleMapRepository;
import net.breezeware.dynamo.organization.dto.CreateOrganizationDto;
import net.breezeware.dynamo.organization.entity.Address;
import net.breezeware.dynamo.organization.entity.Group;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.PasswordResetToken;
import net.breezeware.dynamo.organization.entity.QGroup;
import net.breezeware.dynamo.organization.entity.QRole;
import net.breezeware.dynamo.organization.entity.QUser;
import net.breezeware.dynamo.organization.entity.Role;
import net.breezeware.dynamo.organization.entity.User;
import net.breezeware.dynamo.organization.entity.UserGroupMap;
import net.breezeware.dynamo.organization.entity.UserRegistrationToken;
import net.breezeware.dynamo.organization.entity.UserRoleMap;
import net.breezeware.dynamo.organization.service.api.OrganizationService;
import net.breezeware.dynamo.util.exeption.DynamoDataAccessException;
import net.breezeware.dynamo.util.mail.api.EmailService;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    // @Autowired
    // DynamoAppBootstrapBean dynamoAppBootstrapBean;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRoleMapRepository userRoleMapRepository;

    @Autowired
    UserGroupMapRepository userGroupMapRepository;

    @Autowired
    UserRegistrationTokenRepository userRegistrationTokenRepository;

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordEncoder userPasswordEncoder;

    @Value("${dynamo.applicationServerUrl}")
    private String applicationServerUrl;

    // applicationName
    @Value("${dynamo.applicationName}")
    private String applicationName;

    // applicationAdminEmai
    @Value("${dynamo.applicationAdminEmail}")
    private String applicationAdminEmail;

    @Value("${dynamo.encodePassword}")
    private boolean encodePassword;

    @Value("${dynamo.generateUniqueUserId}")
    private boolean generateUniqueUserId;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve All Organizations", argNames = "")
    public List<Organization> findAllOrganizations() {
        return organizationRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve All Users", argNames = "")
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Users", argNames = "organizationId")
    public List<User> findUsers(long organizationId) {
        return userRepository.findByOrganizationId(organizationId);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Users", argNames = "role")
    public List<User> findUsers(String role) {
        List<User> users = userRepository.findAll();
        List<User> usersByRole = new ArrayList<User>();

        for (User user : users) {
            List<UserRoleMap> urMapList = user.getUserRoleMap();
            for (UserRoleMap urMap : urMapList) {
                if (urMap.getRole().getName().equalsIgnoreCase(role)) {
                    usersByRole.add(user);
                }
            }
        }

        return usersByRole;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Users", argNames = "organizationId, role")
    public List<User> findUsers(long organizationId, String role) {
        List<User> users = userRepository.findByOrganizationId(organizationId);
        List<User> usersByRole = new ArrayList<User>();

        for (User user : users) {
            List<UserRoleMap> urMapList = user.getUserRoleMap();
            for (UserRoleMap urMap : urMapList) {
                if (urMap.getRole().getName().equalsIgnoreCase(role)) {
                    usersByRole.add(user);
                }
            }
        }

        return usersByRole;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Organization", argNames = "organizationId")
    public Organization findOrganizationById(long organizationId) {
        logger.info("Entering findOrganizationById(). organizationId = {}", organizationId);

        Organization organization = organizationRepository.getOne(organizationId);
        logger.info("Leaving findOrganizationById(). organization = {}", organization);

        return organization;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Organization", argNames = "name")
    public Organization findOrganizationByName(String name) {
        logger.info("Entering findOrganizationByName(). organization name = {}", name);

        List<Organization> organizations = organizationRepository.findByNameIgnoreCase(name);

        if (organizations.size() > 0) {
            logger.info("Leaving findOrganizationByName(). organization = {}", organizations.get(0));
            return organizations.get(0);
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve User By Email", argNames = "email")
    public User findByEmailIgnoreCase(String email) {
        User retVal = null;
        List<User> users = userRepository.findByEmailIgnoreCase(email);
        if (users.size() == 1) {
            retVal = users.get(0);
        } else if (users.size() > 1) {
            logger.info("# of users found = " + users.size());
        }

        return retVal;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve User by Unique User ID", argNames = "uniqueUserId")
    public User findByUniqueUserIdIgnoreCase(String uniqueUserId) {
        User user = userRepository.findByUserUniqueId(uniqueUserId);
        if (user != null) {
            logger.info("User found with unique user ID = {}", uniqueUserId);
        }

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Password", argNames = "email")
    public String findPasswordByEmail(String email) {
        logger.info("Entering findPasswordByEmail(). email = {}", email);
        String password = userRepository.findPasswordByEmail(email.trim());
        logger.info("Leaving findPasswordByEmail(). password = {}", password);
        return password;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Password", argNames = "userId")
    public String findPasswordByUserId(long userId) {
        logger.info("Entering findPasswordByUserId(). userId = {}", userId);
        String password = userRepository.findPasswordByUserId(userId);
        logger.info("Leaving findPasswordByUserId(). userID = {}", userId);
        return password;
    }

    // @Transactional
    // @Auditable(event = "Retrieve All Roles", argNames = "")
    // public List<Role> findAllRoles() {
    //
    // List<String> currentUserRoles = dynamoAppBootstrapBean.getCurrentUserRoles();
    //
    // List<Role> allRoles = roleRepository.findAll();
    // List<Role> filteredRoles = new ArrayList<Role>();
    //
    // if (currentUserRoles.contains(Role.USER_ROLE_SYSTEM_ADMIN)) {
    // filteredRoles = allRoles;
    // } else {
    // for (Role role : allRoles) {
    // if (!role.getName().equalsIgnoreCase(Role.USER_ROLE_SYSTEM_ADMIN)) {
    // filteredRoles.add(role);
    // }
    // }
    // }
    //
    // return filteredRoles;
    // }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Roles in Organization", argNames = "")
    public List<Role> getRolesInOrganization(long organizationId) {
        logger.info("Entering getRolesInOrganization()");

        List<Role> roles = roleRepository.findByOrganizationId(organizationId);

        logger.info("Leaving getRolesInOrganization(). # of roles = {}", roles.size());
        return roles;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve All Groups", argNames = "")
    public List<Group> findAllGroups() {
        return groupRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    // @Auditable(event = "getGroups", argNames = "predicate, pageable")
    public Page<Group> getGroups(long organizationId, Predicate predicate, Pageable pageable) {

        // add organization to the predicate before querying for groups
        BooleanBuilder bb = new BooleanBuilder();
        if (predicate != null) {
            logger.info("Predicate class = {}", predicate.getClass());
            if (predicate.getClass().equals(BooleanBuilder.class)) {
                bb = (BooleanBuilder) predicate;
                if (bb.getValue() == null) {
                    bb = new BooleanBuilder();
                }
                bb.and(QGroup.group.organizationId.eq(organizationId));
            } else if (predicate.getClass().equals(BooleanOperation.class)) {
                BooleanOperation bo = (BooleanOperation) predicate;
                bb = new BooleanBuilder();
                bb.and(bo);
                bb.and(QGroup.group.organizationId.eq(organizationId));
            } else if (predicate.getClass().equals(PredicateOperation.class)) {
                PredicateOperation po = (PredicateOperation) predicate;
                bb = new BooleanBuilder();
                bb.and(po);
                bb.and(QGroup.group.organizationId.eq(organizationId));
            }
        } else {
            logger.info("Predicate is null");
            bb.and(QGroup.group.organizationId.eq(organizationId));
        }

        return groupRepository.findAll(bb, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Groups in Organization", argNames = "organizationId")
    public List<Group> getGroupsInOrganization(long organizationId) {
        logger.info("Entering getGroupsInOrganization()");

        List<Group> groups = groupRepository.findByOrganizationId(organizationId);

        logger.info("Leaving getGroupsInOrganization(). # of groups = {}", groups.size());
        return groups;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Save Group", argNames = "group")
    public Group saveGroup(Group group) throws DynamoDataAccessException {

        Group returnGroup = new Group();

        try {
            // check for duplicate group name before saving
            String groupName = group.getName().trim();
            long organizationId = group.getOrganizationId();
            List<Group> existingGroupsWithName = groupRepository.findByNameIgnoreCaseAndOrganizationId(groupName,
                    organizationId);
            if (existingGroupsWithName != null && existingGroupsWithName.size() > 0
                    && group.getId() != existingGroupsWithName.get(0).getId()) {
                throw new DynamoDataAccessException(
                        "Group Name already exists in your organization. Please choose a new one.");
            }

            returnGroup = groupRepository.save(group);
        } catch (DataIntegrityViolationException e) {
            logger.info("Inside DataIntegrityViolationException Catch Block \n" + "getMessage = " + e.getMessage()
                    + "\n" + "getLocalizedMessage() = " + e.getLocalizedMessage() + "\n" + "getCause().getMessage() = "
                    + e.getCause().getMessage() + "\n" + "e.getMostSpecificCause().getMessage() = "
                    + e.getMostSpecificCause().getMessage() + "\n" + "getRootCause().getMessage() = "
                    + e.getRootCause().getMessage() + "\n getRootCause().getLocalizedMessage() = "
                    + e.getRootCause().getLocalizedMessage() + "\n");
            // e.printStackTrace();
            throw new DynamoDataAccessException(e.getRootCause().getMessage());
        } catch (ConstraintViolationException e) {
            logger.info("Inside ConstraintViolationException Catch Block \n" + e.getMessage() + "\n"
                    + e.getCause().getMessage());
            // e.printStackTrace();
            throw new DynamoDataAccessException(e.getCause().getMessage());
        }
        return returnGroup;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Edit Group", argNames = "group")
    public Group editGroup(Group group) {

        group.setModifiedDate(Calendar.getInstance());

        return groupRepository.save(group);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Group", argNames = "groupId")
    public Group findGroup(long groupId) {
        return groupRepository.getOne(groupId);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Role", argNames = "roleId")
    public Role findRole(long roleId) {
        return roleRepository.getOne(roleId);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Role by Name", argNames = "organizationId, roleName")
    public Optional<Role> findRoleByName(long organizationId, String roleName) {
        logger.info("Entering findRoleByName(). Org ID = {}, Role name = {}", organizationId, roleName);

        List<Role> roleList = roleRepository.findByNameIgnoreCaseAndOrganizationId(roleName, organizationId);
        logger.info("Leaving findRoleByName().");

        return Optional.ofNullable(roleList.get(0));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Roles", argNames = "predicate, pageable")
    public Page<Role> getRoles(long organizationId, Predicate predicate, Pageable pageable) {

        // add organization to the predicate before querying for roles
        BooleanBuilder bb = new BooleanBuilder();
        if (predicate != null) {
            logger.info("Predicate class = {}", predicate.getClass());
            if (predicate.getClass().equals(BooleanBuilder.class)) {
                bb = (BooleanBuilder) predicate;
                if (bb.getValue() == null) {
                    bb = new BooleanBuilder();
                }
                bb.and(QRole.role.organizationId.eq(organizationId));
            } else if (predicate.getClass().equals(BooleanOperation.class)) {
                BooleanOperation bo = (BooleanOperation) predicate;
                bb = new BooleanBuilder();
                bb.and(bo);
                bb.and(QRole.role.organizationId.eq(organizationId));
            } else if (predicate.getClass().equals(PredicateOperation.class)) {
                PredicateOperation po = (PredicateOperation) predicate;
                bb = new BooleanBuilder();
                bb.and(po);
                bb.and(QRole.role.organizationId.eq(organizationId));
            }
        } else {
            logger.info("Predicate is null");
            bb.and(QRole.role.organizationId.eq(organizationId));
        }

        return roleRepository.findAll(bb, pageable);
    }

    // @Transactional
    // @Auditable(event = "Retrieve Roles", argNames = "userId")
    // public List<Role> findUserRoles(String userId) {
    // logger.info("Entering findUserRoles(). User ID = {}", userId);
    //
    // List<Role> roles = new ArrayList<Role>();
    //
    // logger.info("Leaving findUserRoles(). # of roles for user = {}");
    // return roles;
    // }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "getUsers", argNames = "predicate, pageable")
    public Page<User> getUsers(Predicate predicate, Pageable pageable) {
        logger.info("Entering getUsers()");

        // check for predicate and call appropriately
        Page<User> pagedUsers = new PageImpl<User>(new ArrayList<User>());
        if (predicate != null) {
            pagedUsers = userRepository.findAll(predicate, pageable);
        } else {
            pagedUsers = userRepository.findAll(pageable);
        }

        // update groups & roles for retrieved users
        for (User user : pagedUsers) {
            user.setUserGroupsCsv(getGroupsCsvString(user.getUserGroupMap()));
            user.setUserRolesCsv(getRolesCsvString(user.getUserRoleMap()));
        }
        logger.info("Leaving getUsers(). # of pagedUsers retrieved = {}, total # = {}",
                pagedUsers.getNumberOfElements(), pagedUsers.getTotalElements());

        return pagedUsers;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "getUsers", argNames = "predicate, pageable")
    public Page<User> getUsers(long organizationId, Predicate predicate, Pageable pageable) {
        logger.info("Entering getUsers()");

        // add user organization to the predicate before querying for users
        BooleanBuilder bb = new BooleanBuilder();
        if (predicate != null) {
            logger.info("Predicate class = {}", predicate.getClass());
            if (predicate.getClass().equals(BooleanBuilder.class)) {
                bb = (BooleanBuilder) predicate;
                if (bb.getValue() == null) {
                    bb = new BooleanBuilder();
                }
                bb.and(QUser.user.organization.id.eq(organizationId));
            } else if (predicate.getClass().equals(BooleanOperation.class)) {
                BooleanOperation bo = (BooleanOperation) predicate;
                bb = new BooleanBuilder();
                bb.and(bo);
                bb.and(QUser.user.organization.id.eq(organizationId));
            } else if (predicate.getClass().equals(PredicateOperation.class)) {
                PredicateOperation po = (PredicateOperation) predicate;
                bb = new BooleanBuilder();
                bb.and(po);
                bb.and(QUser.user.organization.id.eq(organizationId));
            }
        } else {
            logger.info("Predicate is null");
            bb.and(QUser.user.organization.id.eq(organizationId));
        }

        // query with updated predicate
        Page<User> pagedUsers = userRepository.findAll(bb, pageable);

        // update groups & roles for retrieved users
        for (User user : pagedUsers) {
            user.setUserGroupsCsv(getGroupsCsvString(user.getUserGroupMap()));
            user.setUserRolesCsv(getRolesCsvString(user.getUserRoleMap()));
        }
        logger.info("Leaving getUsers(). # of pagedUsers retrieved = {}, total # = {}",
                pagedUsers.getNumberOfElements(), pagedUsers.getTotalElements());

        return pagedUsers;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Organizations", argNames = "predicate, pageable")
    public Page<Organization> getOrganizations(Predicate predicate, Pageable pageable) {
        logger.info("Entering getOrganizations(). Predicate = {}, pageable = {}");

        // check for predicate and call appropriately
        Page<Organization> pagedOrgs = new PageImpl<Organization>(new ArrayList<Organization>());
        if (predicate != null) {
            pagedOrgs = organizationRepository.findAll(predicate, pageable);
        } else {
            pagedOrgs = organizationRepository.findAll(pageable);
        }

        logger.info("Leaving getOrganizations(). # of organizations retrieved = {}", pagedOrgs.getNumberOfElements());

        return pagedOrgs;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Save Role", argNames = "role")
    public Role saveRole(Role role) throws DynamoDataAccessException {
        Role returnRole = new Role();

        try {
            // check for duplicate role name before saving
            String roleName = role.getName().trim();
            long organizationId = role.getOrganizationId();
            List<Role> existingRolesWithName = roleRepository.findByNameIgnoreCaseAndOrganizationId(roleName,
                    organizationId);
            if (existingRolesWithName != null && existingRolesWithName.size() > 0
                    && role.getId() != existingRolesWithName.get(0).getId()) {
                throw new DynamoDataAccessException(
                        "Role Name already exists in your organization. Please choose a new one.");
            }

            returnRole = roleRepository.save(role);
        } catch (DataIntegrityViolationException e) {
            logger.info("Inside DataIntegrityViolationException Catch Block \n" + "getMessage = " + e.getMessage()
                    + "\n" + "getLocalizedMessage() = " + e.getLocalizedMessage() + "\n" + "getCause().getMessage() = "
                    + e.getCause().getMessage() + "\n" + "e.getMostSpecificCause().getMessage() = "
                    + e.getMostSpecificCause().getMessage() + "\n" + "getRootCause().getMessage() = "
                    + e.getRootCause().getMessage() + "\n getRootCause().getLocalizedMessage() = "
                    + e.getRootCause().getLocalizedMessage() + "\n");
            // e.printStackTrace();
            throw new DynamoDataAccessException(e.getRootCause().getMessage());
        } catch (ConstraintViolationException e) {
            logger.info("Inside ConstraintViolationException Catch Block \n" + e.getMessage() + "\n"
                    + e.getCause().getMessage());
            // e.printStackTrace();
            throw new DynamoDataAccessException(e.getCause().getMessage());
        }
        return returnRole;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    // @Auditable(event = "Save User", argNames = "user")
    public User saveUser(User user, boolean encodePassword) throws DynamoDataAccessException {
        logger.info("Entering saveUser(). User = {}", user);

        User returnUser;
        try {
            // get email and check if it already exists
            String email = user.getEmail();
            if (email != null) {
                email = email.trim();
            }

            List<User> existingUsersWithEmail = userRepository.findByEmailIgnoreCase(email);
            if (existingUsersWithEmail != null && existingUsersWithEmail.size() > 0
                    && user.getId() != existingUsersWithEmail.get(0).getId()) {
                throw new DynamoDataAccessException(
                        "Email already exists in the application. Please choose a new one.");
            }

            User existingUserWithUniqueId = userRepository.findByUserUniqueId(user.getUserUniqueId());
            if (existingUserWithUniqueId != null && user.getId() != existingUserWithUniqueId.getId()) {
                throw new DynamoDataAccessException("User ID exists in the application. Please choose a new one.");
            }

            // encode user's password
            if (encodePassword == true && user.getPassword() != null && user.getPassword().trim().length() > 0) {
                user.setPassword(userPasswordEncoder.encode(user.getPassword()));
            }

            // persist the user
            returnUser = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            logger.info("Inside DataIntegrityViolationException Catch Block \n" + "getMessage = " + e.getMessage()
                    + "\n" + "getLocalizedMessage() = " + e.getLocalizedMessage() + "\n" + "getCause().getMessage() = "
                    + e.getCause().getMessage() + "\n" + "e.getMostSpecificCause().getMessage() = "
                    + e.getMostSpecificCause().getMessage() + "\n" + "getRootCause().getMessage() = "
                    + e.getRootCause().getMessage() + "\n getRootCause().getLocalizedMessage() = "
                    + e.getRootCause().getLocalizedMessage() + "\n");
            throw new DynamoDataAccessException(e.getRootCause().getMessage());
        } catch (ConstraintViolationException e) {
            logger.info("Inside ConstraintViolationException Catch Block \n" + e.getMessage() + "\n"
                    + e.getCause().getMessage());
            throw new DynamoDataAccessException(e.getCause().getMessage());
        }

        logger.info("Leaving saveUser().");
        return returnUser;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public User createUser(User user) throws DynamoDataAccessException {
        logger.info("Entering createUser(). User = {}", user);

        // check if user (email) already exists
        String email = user.getEmail();
        if (email != null) {
            email = email.trim();
        }

        List<User> existingUsersWithEmail = userRepository.findByEmailIgnoreCase(email);
        if (existingUsersWithEmail != null && existingUsersWithEmail.size() > 0
                && user.getId() != existingUsersWithEmail.get(0).getId()) {
            throw new DynamoDataAccessException("Email already exists in the application. Please choose a new one.");
        }

        User existingUserWithUniqueId = userRepository.findByUserUniqueId(user.getUserUniqueId());
        if (existingUserWithUniqueId != null && user.getId() != existingUserWithUniqueId.getId()) {
            throw new DynamoDataAccessException("User ID exists in the application. Please choose a new one.");
        }

        // encode user's password
        if (encodePassword == true && user.getPassword() != null && user.getPassword().trim().length() > 0) {
            user.setPassword(userPasswordEncoder.encode(user.getPassword()));
        }

        // generate unique ID for user if required
        String userUniqueId = user.getUserUniqueId();
        if (generateUniqueUserId) {
            userUniqueId = User.generateUniqueId();
            logger.debug("Unique ID generated for user = {}", userUniqueId);
        } else {
            logger.debug("Unique ID '{}' is used from the user input, since generateUniqueUserId = {}", userUniqueId,
                    generateUniqueUserId);
        }
        user.setUserUniqueId(userUniqueId);

        // persist User
        user = userRepository.save(user);

        // persist User Groups
        List<Group> userGroups = findMultipleGroups(user.getUserGroupId());
        List<UserGroupMap> userGroupsMapList = new ArrayList<UserGroupMap>();
        for (Group group : userGroups) {
            UserGroupMap userGroupMap = new UserGroupMap();
            userGroupMap.setCreatedDate(Calendar.getInstance());
            userGroupMap.setModifiedDate(Calendar.getInstance());
            userGroupMap.setGroup(group);
            userGroupMap.setUserId(user.getId());
            userGroupsMapList.add(userGroupMap);
        }
        saveUserGroupMapList(userGroupsMapList);

        // persist User Roles
        List<Role> userRoles = findMultipleRoles(user.getUserRoleId());
        List<UserRoleMap> userRolesMapList = new ArrayList<UserRoleMap>();
        for (Role role : userRoles) {
            UserRoleMap userRoleMap = new UserRoleMap();
            userRoleMap.setCreatedDate(Calendar.getInstance());
            userRoleMap.setModifiedDate(Calendar.getInstance());
            userRoleMap.setRole(role);
            userRoleMap.setUserId(user.getId());
            userRolesMapList.add(userRoleMap);
        }
        saveUserRoleMapList(userRolesMapList);

        logger.info("Leaving createUser()");
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Save Organization", argNames = "organization")
    public Organization saveOrganization(Organization organization) throws DynamoDataAccessException {
        logger.info("Entering saveOrganization(). Organization = {}", organization);

        organization = organizationRepository.save(organization);
        logger.info("Leaving saveOrganization(). Organization = {}", organization);

        return organization;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Groups", argNames = "ids")
    public List<Group> findMultipleGroups(List<Long> ids) {
        logger.info("Entering findMultipleGroups().");

        List<Group> groupsList = new ArrayList<Group>();

        if (ids == null || ids != null && ids.size() <= 0) {
            groupsList = new ArrayList<Group>();
        } else {
            groupsList = groupRepository.findByIdIn(ids);
        }

        logger.info("Leaving findMultipleGroups(). # of groups retrieved = {}", groupsList.size());
        return groupsList;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Roles", argNames = "ids")
    public List<Role> findMultipleRoles(List<Long> ids) {
        logger.info("Entering findMultipleRoles().");

        List<Role> rolesList = new ArrayList<Role>();

        if (ids == null || ids != null && ids.size() <= 0) {
            rolesList = new ArrayList<Role>();
        } else {
            rolesList = roleRepository.findByIdIn(ids);
        }

        logger.info("Leaving findMultipleRoles(). # of roles retrieved = {}", rolesList.size());
        return rolesList;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Save User Group Map", argNames = "userGroupMap")
    public UserGroupMap saveUserGroupMap(UserGroupMap userGroupMap) {
        return userGroupMapRepository.save(userGroupMap);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Save User Role Map", argNames = "userRoleMap")
    public UserRoleMap saveUserRoleMap(UserRoleMap userRoleMap) {
        return userRoleMapRepository.save(userRoleMap);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public UserRegistrationToken createUserRegistrationToken(User user) {
        logger.info("Entering createToken(). User = {}", user);

        UserRegistrationToken token = new UserRegistrationToken();
        token.setTokenCreatedDate(Calendar.getInstance());

        Calendar expDate = Calendar.getInstance();
        expDate.add(Calendar.DATE, 1);

        token.setTokenExpiryDate(expDate);
        String tokenString = UUID.randomUUID().toString();
        token.setToken(tokenString);
        token.setUser(user);
        token = userRegistrationTokenRepository.save(token);

        logger.info("Leaving createToken(), token value = {}", token);
        return token;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve User Registration Token", argNames = "token")
    public UserRegistrationToken getUserRegistrationToken(String token) {
        logger.info("Entering getToken(). Token = {}", token);

        logger.info("Leaving getToken().");
        return userRegistrationTokenRepository.findByTokenIgnoreCase(token);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve User", argNames = "email")
    public User getUserByEmail(String email) {
        logger.info("Entering getUserByEmail(). email = {}", email);

        List<User> users = userRepository.findByEmailIgnoreCase(email);

        if (users == null || users.size() == 0) {
            logger.info("Leaving getUserByEmail(). User with email = {} not found.", email);
            return null;
        } else {
            User user = users.get(0);
            logger.info("Leaving getUserByEmail(). User with email = {} found. User ID = {}.", email, user.getId());
            return user;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve User", argNames = "userUniqueId")
    public User getUserByUserUniqueId(String userUniqueId) {
        logger.info("Entering getUserByUserUniqueId(). uniqueUserId = {}", userUniqueId);

        User user = userRepository.findByUserUniqueId(userUniqueId);
        logger.info("Leaving getUserByUserUniqueId(). User = {}", user);

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve User", argNames = "id")
    public User getUserById(long userId) {
        logger.info("Entering getUserById(). userId = {}", userId);

        User user = userRepository.getOne(userId);

        logger.info("Leaving getUserById(). User with userId = {} found. User  = {}.", userId, user);
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Delete User Gruop Map", argNames = "userGroupMap")
    public void deleteUserGroupMap(UserGroupMap userGroupMap) {
        userGroupMapRepository.delete(userGroupMap);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Delete User Role Map", argNames = "userRoleMap")
    public void deleteUserRoleMap(UserRoleMap userRoleMap) {
        userRoleMapRepository.delete(userRoleMap);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Create Password Reset Token", argNames = "user")
    public PasswordResetToken createPasswordResetToken(User user) {
        logger.info("Entering createToken(). User = {}", user);

        PasswordResetToken token = new PasswordResetToken();
        token.setTokenCreatedDate(Calendar.getInstance());

        Calendar expDate = Calendar.getInstance();
        expDate.add(Calendar.DATE, 1);

        token.setTokenExpiryDate(expDate);
        String tokenString = UUID.randomUUID().toString();
        token.setToken(tokenString);
        token.setUser(user);

        logger.info("Leaving createToken()");
        return passwordResetTokenRepository.save(token);

    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Password Reset Token", argNames = "token")
    public PasswordResetToken getPasswordResetToken(String token) {
        logger.info("Entering getToken(). Token = {}", token);

        logger.info("Leaving getToken().");
        return passwordResetTokenRepository.findByTokenIgnoreCase(token);

    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Create Organization with Default User", argNames = "createOrganizationDto")
    public Organization createOrganizationWithDefaultUser(CreateOrganizationDto createOrganizationDto)
            throws DynamoDataAccessException {
        logger.info("Entering createOrganizationWithDefaultUser()");

        // ** create an organization from createOrganizationDto **//

        // throw exception if duplicate organization name exists
        String orgName = createOrganizationDto.getOrganizationName().trim();
        long organizationId = -1;
        List<Organization> existingOrgsWithName = organizationRepository.findByNameIgnoreCase(orgName);
        if (existingOrgsWithName != null && existingOrgsWithName.size() > 0
                && organizationId != existingOrgsWithName.get(0).getId()) {
            throw new DynamoDataAccessException("Organization Name already exists. Please choose a new one.");
        }

        // save organization
        Organization organization = CreateOrganizationDto.createOrganizationFromDto(createOrganizationDto);
        organization = organizationRepository.save(organization);
        logger.info("ID of newly created organization = {}", organization.getId());

        // save organization Address if available
        Optional<Address> orgAddrOpt = CreateOrganizationDto.createAddressFromDto(createOrganizationDto);
        if (orgAddrOpt.isPresent()) {
            addressRepository.save(orgAddrOpt.get());
            logger.debug("Address for organization = {}", orgAddrOpt.get());
        }

        // ** create default user from createOrganizationDto **//
        User user = CreateOrganizationDto.createUserFromDto(organization, createOrganizationDto);

        // throw exception if duplicate email name exists
        String email = user.getEmail();
        if (email != null) {
            email = email.trim();
        }
        List<User> existingUsersWithEmail = userRepository.findByEmailIgnoreCase(email);
        if (existingUsersWithEmail != null && existingUsersWithEmail.size() > 0
                && user.getId() != existingUsersWithEmail.get(0).getId()) {
            throw new DynamoDataAccessException("Email already exists in the application. Please choose a new one.");
        }

        // save user
        user.setStatus(User.STATUS_NEW);
        user = userRepository.save(user);
        logger.info("ID of newly created user = {}", user.getId());

        // ** create an ORGANIZATION_ADMIN role for this organization **//
        Role role = new Role();
        role.setCreatedDate(Calendar.getInstance());
        role.setModifiedDate(Calendar.getInstance());
        role.setDescription("Organization Admin");
        role.setName(Role.USER_ROLE_ORGANIZATION_ADMIN);
        role.setOrganizationId(organization.getId());
        role = roleRepository.save(role);

        // ** map ORGANIZATION_ADMIN role to default user **//
        UserRoleMap userRoleMap = new UserRoleMap();
        userRoleMap.setCreatedDate(Calendar.getInstance());
        userRoleMap.setModifiedDate(Calendar.getInstance());
        userRoleMap.setRole(role);
        userRoleMap.setUserId(user.getId());
        saveUserRoleMap(userRoleMap);

        // ** create a user registration token for user **//
        UserRegistrationToken userRegistrationToken = createUserRegistrationToken(user);
        logger.info("Created user registration token");

        // ** email the user registration token for user **//
        Map<String, String> keyVals = new HashMap<String, String>();
        keyVals.put("applicationName", applicationName);
        keyVals.put("firstName", userRegistrationToken.getUser().getFirstName());
        keyVals.put("lastName", userRegistrationToken.getUser().getLastName());

        // get server address from Dynamo bootstrap bean
        String serverAddress = applicationServerUrl;
        keyVals.put("registrationLink", serverAddress + "/registerUser?token=" + userRegistrationToken.getToken());
        String templateName = "user-registration-email-template";
        logger.info("Sending email with template name = {}", templateName);

        emailService.sendMail(applicationAdminEmail, user.getEmail(), applicationName + " - Complete Signup.", keyVals,
                templateName);
        logger.info("Sent email to user with email {}", user.getEmail());

        logger.info("Leaving createOrganizationWithDefaultUser(). Organization ID = {}", organization.getId());
        return organization;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public List<User> retrieveUsersByRole(long organizationId, List<String> roles) {
        logger.info("Entering retrieveUsersByRole(). Organization ID = {}, roles = {}", organizationId, roles);

        List<User> usersByRoles = userRepository.findByOrganizationIdAndUserRoleMapRoleNameIn(organizationId, roles);

        logger.info("Leaving retrieveUsersByRole(). # of users by roles = {}", usersByRoles.size());
        return usersByRoles;
    }

    private String getGroupsCsvString(List<UserGroupMap> userGroupMapList) {
        StringBuilder sb = new StringBuilder();

        int index = 0;
        for (UserGroupMap userGroupMap : userGroupMapList) {
            sb.append(userGroupMap.getGroup().getName());
            index++;
            if (index < userGroupMapList.size()) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }

    private String getRolesCsvString(List<UserRoleMap> userRoleMapList) {
        StringBuilder sb = new StringBuilder();

        int index = 0;
        for (UserRoleMap userRoleMap : userRoleMapList) {
            sb.append(userRoleMap.getRole().getName());
            index++;
            if (index < userRoleMapList.size()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private List<UserGroupMap> saveUserGroupMapList(List<UserGroupMap> userGroupMapList) {
        for (UserGroupMap ugMap : userGroupMapList) {
            ugMap = userGroupMapRepository.save(ugMap);
        }

        return userGroupMapList;
    }

    private List<UserRoleMap> saveUserRoleMapList(List<UserRoleMap> userRoleMapList) {
        for (UserRoleMap urMap : userRoleMapList) {
            urMap = userRoleMapRepository.save(urMap);
        }

        return userRoleMapList;
    }

}
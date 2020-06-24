package net.breezeware.dynamo.organization.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.querydsl.core.types.Predicate;

import net.breezeware.dynamo.config.DynamoConfigProperties;
import net.breezeware.dynamo.organization.dto.CreateOrganizationDto;
import net.breezeware.dynamo.organization.entity.Group;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.Role;
import net.breezeware.dynamo.organization.entity.User;
import net.breezeware.dynamo.organization.entity.UserGroupMap;
import net.breezeware.dynamo.organization.entity.UserRegistrationToken;
import net.breezeware.dynamo.organization.entity.UserRoleMap;
import net.breezeware.dynamo.organization.service.api.OrganizationService;
import net.breezeware.dynamo.organization.util.OrganizationManagementUtil;
import net.breezeware.dynamo.util.DynamoAppBootstrapBean;
import net.breezeware.dynamo.util.exeption.DynamoDataAccessException;
import net.breezeware.dynamo.util.mail.api.EmailService;

/**
 * Controller methods for roles, users and group services.
 *
 */
// @Profile("DBAuthentication")
@Controller
@SessionAttributes("currentUser")
@RequestMapping(value = "/organization/*")
@PreAuthorize("hasAnyAuthority('ROLE_SYSTEM_ADMIN', 'ORGANIZATION_ADMIN')")
@Profile("!service-layer-test")
public class OrganizationManagementController {

    Logger logger = LoggerFactory.getLogger(OrganizationManagementController.class);

    @Autowired
    OrganizationService organizationService;

    @Autowired
    EmailService emailService;

    @Autowired
    DynamoConfigProperties dynamoConfigProperties;

    @Autowired
    DynamoAppBootstrapBean dynamoAppBootstrapBean;

    @Value("${dynamo.requireUserRegistrationByEmail}")
    private boolean requireUserRegistrationByEmail;

    @Value("${dynamo.generateUniqueUserId}")
    private boolean generateUniqueUserId;

    @Value("${dynamo.encodePassword}")
    private boolean encodePassword;

    @Value("${dynamo.applicationServerUrl}")
    private String applicationServerUrl;

    // applicationName
    @Value("${dynamo.applicationName}")
    private String applicationName;

    // applicationOwner
    @Value("${dynamo.applicationOwner}")
    private String applicationOwner;

    // applicationAdminEmai
    @Value("${dynamo.applicationAdminEmail}")
    private String applicationAdminEmail;

    @Autowired
    OrganizationManagementUtil organizationManagementUtil;

    /**
     * Redirects to the all-groups page displaying the list of groups.
     * 
     * @param model
     * @param predicate
     * @param pageable
     * @param parameters
     * @return
     */
    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public String listGroups(Model model, @QuerydslPredicate(root = Group.class) Predicate predicate,
            @PageableDefault(sort = { "name" }, page = 0, size = 12) Pageable pageable,
            @RequestParam MultiValueMap<String, String> parameters, HttpSession session) {

        logger.info("# of params = " + parameters.size());

        long organizationId = organizationManagementUtil.getOrganizationIdFromSession(session);
        Page<Group> pagedGroups = organizationService.getGroups(organizationId, predicate, pageable);
        logger.info("# of groups fetched = {}", pagedGroups.getNumberOfElements());

        model.addAttribute("pagedGroups", pagedGroups);
        model.addAttribute("activeNav", "groups");

        return "dynamo-organization/usermanagement/all-groups";
    }

    /**
     * Redirects to the all-roles page displaying the list of roles.
     */
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public String listRoles(Model model, @QuerydslPredicate(root = Role.class) Predicate predicate,
            @PageableDefault(sort = { "name" }, page = 0, size = 12) Pageable pageable,
            @RequestParam MultiValueMap<String, String> parameters, HttpSession session) {

        logger.info("# of params = " + parameters.size());

        long organizationId = organizationManagementUtil.getOrganizationIdFromSession(session);
        Page<Role> pagedRoles = organizationService.getRoles(organizationId, predicate, pageable);
        logger.info("# of roles fetched = {}", pagedRoles.getNumberOfElements());

        model.addAttribute("pagedRoles", pagedRoles);
        model.addAttribute("activeNav", "roles");

        return "dynamo-organization/usermanagement/all-roles";
    }

    /**
     * Redirects to the all-users page displaying the list of users across
     * organizations.
     */
    @PreAuthorize("hasAnyAuthority('ROLE_SYSTEM_ADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String listUsers(Model model, @QuerydslPredicate(root = User.class) Predicate predicate,
            @PageableDefault(sort = { "email" }, page = 0, size = 12) Pageable pageable,
            @RequestParam MultiValueMap<String, String> parameters, HttpSession session) {

        logger.info("# of params = " + parameters.size());

        Page<User> pagedUsers = organizationService.getUsers(predicate, pageable);

        logger.info("# of users fetched = {}", pagedUsers.getNumberOfElements());

        model.addAttribute("pagedUsers", pagedUsers);
        model.addAttribute("activeNav", "users");

        return "dynamo-organization/usermanagement/all-users";
    }

    /**
     * Redirects to the all-users page displaying the list of users in the user's
     * organization.
     */
    @RequestMapping(value = "/orgUsers", method = RequestMethod.GET)
    public String listOrganizationUsers(Model model, @QuerydslPredicate(root = User.class) Predicate predicate,
            @PageableDefault(sort = { "email" }, page = 0, size = 12) Pageable pageable,
            @RequestParam MultiValueMap<String, String> parameters, HttpSession session) {

        logger.info("# of params = " + parameters.size());

        long organizationId = organizationManagementUtil.getOrganizationIdFromSession(session);
        Page<User> pagedUsers = organizationService.getUsers(organizationId, predicate, pageable);

        logger.info("# of users fetched = {}", pagedUsers.getNumberOfElements());

        model.addAttribute("pagedUsers", pagedUsers);
        model.addAttribute("activeNav", "users");

        return "dynamo-organization/usermanagement/all-users";
    }

    /**
     * Redirects to all-organizations page displaying the list of organizations.
     */
    @PreAuthorize("hasAuthority('ROLE_SYSTEM_ADMIN')")
    @RequestMapping(value = "/organizations", method = RequestMethod.GET)
    public String listOrganizations(Model model, @QuerydslPredicate(root = Organization.class) Predicate predicate,
            @PageableDefault(sort = { "name" }, page = 0, size = 12) Pageable pageable,
            @RequestParam MultiValueMap<String, String> parameters) {

        logger.info("# of params = " + parameters.size());

        Page<Organization> pagedOrganizations = organizationService.getOrganizations(predicate, pageable);
        logger.info("# of organizations fetched = {}", pagedOrganizations.getNumberOfElements());

        model.addAttribute("pagedOrganizations", pagedOrganizations);
        model.addAttribute("activeNav", "organizations");

        return "dynamo-organization/usermanagement/all-organizations";
    }

    /**
     * Redirects to the create-group page.
     */
    @RequestMapping(value = "/createGroup", method = RequestMethod.GET)
    public String createGroup(Model model, HttpSession session) {

        Group group = new Group();
        model.addAttribute("group", group);
        model.addAttribute("activeNav", "groups");
        return "dynamo-organization/usermanagement/create-group";
    }

    /**
     * Gets the group instance from create-group page,if any errors redirects to the
     * create-group page else forwards to the all-groups page.
     * 
     * @param group         Gets the populated group instance from the create-group
     *                      page.
     * @param bindingResult Captures errors present in the form.
     */
    @RequestMapping(value = "/createGroup", method = RequestMethod.POST)
    public ModelAndView createGroup(@Valid @ModelAttribute("group") Group group, BindingResult bindingResult,
            Model model, HttpSession session) {
        group.setCreatedDate(Calendar.getInstance());
        group.setModifiedDate(Calendar.getInstance());

        logger.info("Group = {}", group);

        if (bindingResult.hasErrors()) {
            logger.info("There are binding result errors. # of field errors = {}. # of all errors = {}.",
                    bindingResult.getFieldErrors().size(), bindingResult.getAllErrors().size());
            for (FieldError fe : bindingResult.getFieldErrors()) {
                logger.info("Field error = " + fe.getField().toString() + ", " + fe.getDefaultMessage() + ", "
                        + fe.getCode());
            }

            model.addAttribute("group", group);
            model.addAttribute("activeNav", "groups");
            return new ModelAndView("dynamo-organization/usermanagement/create-group");
        } else {
            try {
                // update organization ID for group
                long organizationId = organizationManagementUtil.getOrganizationIdFromSession(session);
                group.setOrganizationId(organizationId);

                // persist group
                organizationService.saveGroup(group);
            } catch (DynamoDataAccessException e) {
                model.addAttribute("group", group);
                model.addAttribute("activeNav", "groups");

                bindingResult.addError(new ObjectError("group", e.getMessage()));

                return new ModelAndView("dynamo-organization/usermanagement/create-group");
            }
        }

        model.addAttribute("activeNav", "groups");
        return new ModelAndView("redirect:/organization/groups");
    }

    /**
     * Redirects to the create-role page.
     */
    @RequestMapping(value = "/createRole", method = RequestMethod.GET)
    public String createRole(Model model, HttpSession session) {

        Role role = new Role();
        model.addAttribute("role", role);
        model.addAttribute("activeNav", "roles");
        return "dynamo-organization/usermanagement/create-role";
    }

    /**
     * Gets the role instance from create-role page and forwards to the all-roles
     * page.
     * 
     * @param role
     * @param bindingResult
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/createRole", method = RequestMethod.POST)
    public ModelAndView createRole(@Valid @ModelAttribute("role") Role role, BindingResult bindingResult, Model model,
            HttpSession session) {
        role.setCreatedDate(Calendar.getInstance());
        role.setModifiedDate(Calendar.getInstance());

        logger.info("Role = {}", role);

        if (bindingResult.hasErrors()) {
            logger.info("There are binding result errors. # of field errors = {}. # of all errors = {}.",
                    bindingResult.getFieldErrors().size(), bindingResult.getAllErrors().size());
            for (FieldError fe : bindingResult.getFieldErrors()) {
                logger.info("Field error = " + fe.getField().toString() + ", " + fe.getDefaultMessage() + ", "
                        + fe.getCode());
            }

            model.addAttribute("role", role);
            model.addAttribute("activeNav", "roles");
            return new ModelAndView("dynamo-organization/usermanagement/create-role");
        } else {
            try {
                // update organization ID for role
                long organizationId = organizationManagementUtil.getOrganizationIdFromSession(session);
                role.setOrganizationId(organizationId);

                // persist role
                organizationService.saveRole(role);
            } catch (DynamoDataAccessException e) {
                model.addAttribute("role", role);
                model.addAttribute("activeNav", "roles");

                bindingResult.addError(new ObjectError("role", e.getMessage()));

                return new ModelAndView("dynamo-organization/usermanagement/create-role");
            }
        }

        model.addAttribute("activeNav", "roles");
        return new ModelAndView("redirect:/organization/roles");
    }

    /**
     * Redirects to the edit-group page.
     */
    @RequestMapping(value = "/editGroup", method = RequestMethod.GET)
    public String editGroup(@RequestParam("id") String id, Model model) {

        Group group = organizationService.findGroup(Long.valueOf(id));

        logger.info("Group details = {}", group);

        model.addAttribute("activeNav", "groups");
        model.addAttribute("group", group);
        return "dynamo-organization/usermanagement/edit-group";

    }

    /**
     * Gets the group instance from edit-group page,if any errors redirects to the
     * edit-group page else forwards to the all-groups page.
     * 
     * @param group         Gets the populated group instance from the edit-group
     *                      page.
     * @param bindingResult Captures errors present in the form.
     */
    @RequestMapping(value = "/editGroup", method = RequestMethod.POST)
    public ModelAndView editGroup(@Valid @ModelAttribute("group") Group group, BindingResult bindingResult,
            Model model) {

        logger.info(group.toString());

        if (bindingResult.hasErrors()) {
            logger.info("There are binding result errors. # of field errors = {}. # of all errors = {}.",
                    bindingResult.getFieldErrors().size(), bindingResult.getAllErrors().size());
            for (FieldError fe : bindingResult.getFieldErrors()) {
                logger.info("Field error = " + fe.getField().toString() + ", " + fe.getDefaultMessage() + ", "
                        + fe.getCode());
            }

            model.addAttribute("group", group);
            model.addAttribute("activeNav", "groups");
            return new ModelAndView("dynamo-organization/usermanagement/edit-group");
        } else {
            try {
                group.setModifiedDate(Calendar.getInstance());
                organizationService.saveGroup(group);
            } catch (DynamoDataAccessException e) {
                model.addAttribute("group", group);
                model.addAttribute("activeNav", "groups");

                bindingResult.addError(new ObjectError("group", e.getMessage()));

                return new ModelAndView("dynamo-organization/usermanagement/edit-group");
            }
        }

        model.addAttribute("activeNav", "groups");
        return new ModelAndView("redirect:/organization/groups");
    }

    /**
     * Redirects to the edit-role page.
     */
    @RequestMapping(value = "/editRole", method = RequestMethod.GET)
    public String editRole(@RequestParam("id") String id, Model model) {

        Role role = organizationService.findRole(Long.valueOf(id));

        logger.info("Role details = {}", role);

        model.addAttribute("activeNav", "roles");
        model.addAttribute("role", role);
        return "dynamo-organization/usermanagement/edit-role";

    }

    /**
     * Gets the role instance from edit-role page,if any errors redirects to the
     * edit-role page else forwards to the all-roles page.
     * 
     * @param role          Gets the populated role instance from the edit-role
     *                      page.
     * @param bindingResult Captures errors present in the form.
     */
    @RequestMapping(value = "/editRole", method = RequestMethod.POST)
    public ModelAndView editRole(@Valid @ModelAttribute("role") Role role, BindingResult bindingResult, Model model) {

        logger.info(role.toString());

        if (bindingResult.hasErrors()) {
            logger.info("There are binding result errors. # of field errors = {}. # of all errors = {}.",
                    bindingResult.getFieldErrors().size(), bindingResult.getAllErrors().size());
            for (FieldError fe : bindingResult.getFieldErrors()) {
                logger.info("Field error = " + fe.getField().toString() + ", " + fe.getDefaultMessage() + ", "
                        + fe.getCode());
            }

            model.addAttribute("role", role);
            model.addAttribute("activeNav", "roles");
            return new ModelAndView("dynamo-organization/usermanagement/edit-role");
        } else {
            try {
                role.setModifiedDate(Calendar.getInstance());
                organizationService.saveRole(role);
            } catch (DynamoDataAccessException e) {
                model.addAttribute("role", role);
                model.addAttribute("activeNav", "roles");

                bindingResult.addError(new ObjectError("role", e.getMessage()));

                return new ModelAndView("dynamo-organization/usermanagement/edit-role");
            }
        }

        model.addAttribute("activeNav", "roles");
        return new ModelAndView("redirect:/organization/roles");
    }

    /**
     * Redirects to the create-user page.
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    public String createUser(Model model, HttpSession session) {

        logger.info("Entering createUser Controller : GET");
        User user = new User();

        // get organization ID of user who is currently logged-in
        long organizationId = organizationManagementUtil.getOrganizationIdFromSession(session);

        // get groups and roles defined for this organization and use it in UI
        List<Group> groups = organizationService.getGroupsInOrganization(organizationId);
        List<Role> roles = organizationService.getRolesInOrganization(organizationId);

        logger.info("Session User : " + session.getAttribute("currentUser"));
        logger.info("List of Groups : " + groups);
        logger.info("List of Roles : " + roles);
        model.addAttribute("user", user);
        model.addAttribute("groups", groups);
        model.addAttribute("roles", roles);
        model.addAttribute("userTimeZoneOptions", User.userTimeZoneOptions());
        model.addAttribute("activeNav", "users");
        logger.info("Exiting createUser Controller : GET");
        return "dynamo-organization/usermanagement/create-user";
    }

    /**
     * Gets the user instance from create-user page,if any errors redirects to the
     * create-user page else forwards to the all-users page.
     * 
     * @param user
     * @param bindingResult
     * @param session
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public ModelAndView createUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
            HttpSession session, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        logger.info("Entering Create User : POST ");

        boolean hasErrors = false;

        // binding errors caught by java validation framework
        if (bindingResult.hasErrors()) {
            logger.info("There are binding result errors. # of field errors = {}. # of all errors = {}.",
                    bindingResult.getFieldErrors().size(), bindingResult.getAllErrors().size());
            for (FieldError fe : bindingResult.getFieldErrors()) {
                logger.info("Field error = " + fe.getField().toString() + ", " + fe.getDefaultMessage() + ", "
                        + fe.getCode());
            }
            hasErrors = true;
        }

        // custom validation errors
        if (user.getUserGroupId() == null || user.getUserGroupId() != null && user.getUserGroupId().size() == 0) {
            bindingResult.addError(new ObjectError("user", "Minimum 1 group should be selected."));
            hasErrors = true;
        }
        if (user.getUserRoleId() == null || user.getUserRoleId() != null && user.getUserRoleId().size() == 0) {
            bindingResult.addError(new ObjectError("user", "Minimum 1 role should be selected."));
            hasErrors = true;
        }

        // return to the create user screen if there are binding errors
        if (hasErrors == true) {
            logger.info("There are binding errors. Returning to create user screen");

            List<Group> groups = organizationService
                    .getGroupsInOrganization(dynamoAppBootstrapBean.getCurrentUserOrganizationId());
            List<Role> roles = organizationService
                    .getRolesInOrganization(dynamoAppBootstrapBean.getCurrentUserOrganizationId());
            model.addAttribute("groups", groups);
            model.addAttribute("roles", roles);
            model.addAttribute("userTimeZoneOptions", User.userTimeZoneOptions());
            model.addAttribute("user", user);
            model.addAttribute("activeNav", "users");

            return new ModelAndView("dynamo-organization/usermanagement/create-user");
        }
        // proceed with user creation if there are no errors
        else {
            List<Group> groupList = organizationService.findMultipleGroups(user.getUserGroupId());
            List<Role> roleList = organizationService.findMultipleRoles(user.getUserRoleId());

            // generate unique ID for user if required
            String userUniqueId = user.getUserUniqueId();
            if (generateUniqueUserId) {
                userUniqueId = User.generateUniqueId();
                logger.debug("Unique ID generated for user = {}", userUniqueId);
            } else {
                logger.debug("Unique ID '{}' is used from the user input, since generateUniqueUserId = {}",
                        userUniqueId, generateUniqueUserId);
            }

            // create the user
            user.setUserUniqueId(userUniqueId);
            user.setCreatedDate(Calendar.getInstance());
            user.setModifiedDate(Calendar.getInstance());

            if (requireUserRegistrationByEmail) {
                logger.debug("requireUserRegistrationByEmail = {}. So setting user status to new ",
                        requireUserRegistrationByEmail);
                user.setStatus(User.STATUS_NEW);
            } else {
                logger.debug("requireUserRegistrationByEmail = {}. So setting user status to active ",
                        requireUserRegistrationByEmail);
                user.setStatus(User.STATUS_ACTIVE);
            }

            // save the user entity
            long organizationId = organizationManagementUtil.getOrganizationIdFromSession(session);
            user.setOrganization(organizationService.findOrganizationById(organizationId));
            try {
                organizationService.saveUser(user, encodePassword);
            } catch (DynamoDataAccessException e) {
                List<Group> groups = organizationService.findAllGroups();
                List<Role> roles = organizationService
                        .getRolesInOrganization(dynamoAppBootstrapBean.getCurrentUserOrganizationId());
                model.addAttribute("groups", groups);
                model.addAttribute("roles", roles);
                model.addAttribute("user", user);
                model.addAttribute("activeNav", "users");

                bindingResult.addError(new ObjectError("user", e.getMessage()));

                return new ModelAndView("dynamo-organization/usermanagement/create-user");
            }

            // create roles and groups for user
            if (groupList != null && roleList != null) {
                for (Group group : groupList) {
                    UserGroupMap userGroupMap = new UserGroupMap();
                    userGroupMap.setCreatedDate(Calendar.getInstance());
                    userGroupMap.setModifiedDate(Calendar.getInstance());
                    userGroupMap.setGroup(group);
                    userGroupMap.setUserId(user.getId());
                    organizationService.saveUserGroupMap(userGroupMap);
                    logger.info("Saved user group map");
                }

                for (Role role : roleList) {
                    UserRoleMap userRoleMap = new UserRoleMap();
                    userRoleMap.setCreatedDate(Calendar.getInstance());
                    userRoleMap.setModifiedDate(Calendar.getInstance());
                    userRoleMap.setRole(role);
                    userRoleMap.setUserId(user.getId());
                    organizationService.saveUserRoleMap(userRoleMap);
                    logger.info("Saved user role map");
                }
            }

            if (requireUserRegistrationByEmail) {
                // create a user registration token for user
                UserRegistrationToken userRegistrationToken = organizationService.createUserRegistrationToken(user);
                logger.info("Created user registration token");

                // email the user registration token for user
                Map<String, String> keyVals = new HashMap<String, String>();
                keyVals.put("applicationName", applicationName);
                keyVals.put("applicationOwner", applicationOwner);
                keyVals.put("firstName", userRegistrationToken.getUser().getFirstName());
                keyVals.put("lastName", userRegistrationToken.getUser().getLastName());
                keyVals.put("registrationLink",
                        applicationServerUrl + "/registerUser?token=" + userRegistrationToken.getToken());
                String templateName = "user-registration-email-template";
                logger.info("Sending email with template name = {}", templateName);

                String emailSubject = applicationName + " - Complete Signup";
                emailService.sendMail(applicationAdminEmail, user.getEmail(), emailSubject, keyVals, templateName);

                logger.info("Sent email!");
            } else {
                logger.info("Since requireUserRegistrationByEmail is set to {}, Email is not being sent.",
                        requireUserRegistrationByEmail);
            }

            model.addAttribute("activeNav", " users");
            logger.info("Exiting Create User :POST");

            redirectAttributes.addFlashAttribute("successMessage",
                    "New user registration initiated successfully. User will receive an email with an activation link.");

            Map<String, Object> mavAttributes = new HashMap<String, Object>();
            mavAttributes.put("userFromDynamo", user);

            // return to the list of users
            return new ModelAndView("redirect:/organization/orgUsers", mavAttributes);
        }
    }

    /**
     * Gets the user by user ID and re-directs to the edit-user page.
     * 
     * @param userId
     * @param model
     * @return returns a string to the edit-user template.
     */
    @RequestMapping(value = "/editUser", method = RequestMethod.GET)
    public String editUser(@RequestParam("userId") long userId, Model model, HttpSession session) {
        User user = organizationService.getUserById(userId);
        logger.info("User Details : " + user);

        // get organization Id of the user that is selected for edit
        long organizationId = user.getOrganization().getId();

        // get groups and roles of the user organization selected for edit
        List<Group> groups = organizationService.getGroupsInOrganization(organizationId);
        List<Role> roles = organizationService.getRolesInOrganization(organizationId);

        model.addAttribute("groups", groups);
        model.addAttribute("roles", roles);
        model.addAttribute("activeNav", "users");
        model.addAttribute("userTimeZoneOptions", User.userTimeZoneOptions());

        if (user.getUserGroupMap() != null) {
            List<Long> selectedGroupIds = new ArrayList<Long>();
            for (UserGroupMap map : user.getUserGroupMap()) {
                selectedGroupIds.add(map.getGroup().getId());
            }
            user.setUserGroupId(selectedGroupIds);
        }

        if (user.getUserRoleMap() != null) {
            List<Long> selectedRoleIds = new ArrayList<Long>();
            for (UserRoleMap map : user.getUserRoleMap()) {
                selectedRoleIds.add(map.getRole().getId());
            }
            user.setUserRoleId(selectedRoleIds);
        }

        model.addAttribute("user", user);

        return "dynamo-organization/usermanagement/edit-user";
    }

    /**
     * 
     * Gets the populated user.If there exist errors then redirects to the edit-user
     * page,else redirects to the all-users page.
     * 
     * @param user          Populated entity.
     * @param bindingResult
     * @param model
     * @return returns to a template/controller mapping.
     */
    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public ModelAndView editUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {
        logger.info("Entering Edit User:POST");
        logger.info("Usergroupmap : " + user.getUserGroupMap());
        logger.info("Userrolemap : " + user.getUserRoleMap());
        boolean hasErrors = false;

        if (bindingResult.hasErrors()) {
            logger.info("There are binding result errors. # of field errors = {}. # of all errors = {}.",
                    bindingResult.getFieldErrors().size(), bindingResult.getAllErrors().size());
            for (FieldError fe : bindingResult.getFieldErrors()) {
                logger.info("Field error = " + fe.getField().toString() + ", " + fe.getDefaultMessage() + ", "
                        + fe.getCode());
            }
            hasErrors = true;
        }

        if (user.getUserGroupId() == null || user.getUserGroupId() != null && user.getUserGroupId().size() == 0) {
            bindingResult.addError(new ObjectError("user", "Minimum 1 group should be selected."));
            hasErrors = true;
        }
        if (user.getUserRoleId() == null || user.getUserRoleId() != null && user.getUserRoleId().size() == 0) {
            bindingResult.addError(new ObjectError("user", "Minimum 1 role should be selected."));
            hasErrors = true;
        }

        if (hasErrors == false) {

            // FIXME: move the logic of creating user and its sub properties (including
            // user-group, user-roles) to the service layer.

            try {
                user.setModifiedDate(Calendar.getInstance());

                // NOTE: set the password retrieved from the DB (This is not required for ALSTOM
                // where user can edit his password in the edit-user screen)
                boolean isPasswordRetrievedFromDb = false;
                if (user.getPassword() == null
                        || (user.getPassword() != null && user.getPassword().trim().length() <= 0)) {
                    String password = organizationService.findPasswordByUserId(user.getId());
                    user.setPassword(password);
                    isPasswordRetrievedFromDb = true;
                }

                if (isPasswordRetrievedFromDb) {
                    organizationService.saveUser(user, false);
                } else {
                    organizationService.saveUser(user, encodePassword);
                }

                // Deleting the usergroupmap
                for (UserGroupMap map : user.getUserGroupMap()) {
                    organizationService.deleteUserGroupMap(map);
                }
                // Deleting the userrolemap
                for (UserRoleMap map : user.getUserRoleMap()) {
                    organizationService.deleteUserRoleMap(map);
                }

                // Creating new groups & roles
                List<Group> groupList = organizationService.findMultipleGroups(user.getUserGroupId());
                List<Role> roleList = organizationService.findMultipleRoles(user.getUserRoleId());

                for (Group group : groupList) {
                    UserGroupMap userGroupMap = new UserGroupMap();
                    userGroupMap.setCreatedDate(Calendar.getInstance());
                    userGroupMap.setModifiedDate(Calendar.getInstance());
                    userGroupMap.setGroup(group);
                    userGroupMap.setUserId(user.getId());
                    organizationService.saveUserGroupMap(userGroupMap);
                }

                for (Role role : roleList) {
                    UserRoleMap userRoleMap = new UserRoleMap();
                    userRoleMap.setCreatedDate(Calendar.getInstance());
                    userRoleMap.setModifiedDate(Calendar.getInstance());
                    userRoleMap.setRole(role);
                    userRoleMap.setUserId(user.getId());
                    organizationService.saveUserRoleMap(userRoleMap);
                }

                model.addAttribute("activeNav", "user");

                redirectAttributes.addFlashAttribute("successMessage", "User details were saved successfully");

                return new ModelAndView("redirect:/organization/orgUsers");
            } catch (DynamoDataAccessException e) {
                model.addAttribute("user", user);
                model.addAttribute("activeNav", "users");

                bindingResult.addError(new ObjectError("user", e.getMessage()));

                hasErrors = true;
            }
        }
        if (hasErrors == true) {
            List<Group> groups = organizationService
                    .getGroupsInOrganization(dynamoAppBootstrapBean.getCurrentUserOrganizationId());
            List<Role> roles = organizationService
                    .getRolesInOrganization(dynamoAppBootstrapBean.getCurrentUserOrganizationId());
            model.addAttribute("groups", groups);
            model.addAttribute("roles", roles);
            model.addAttribute("user", user);
            model.addAttribute("userTimeZoneOptions", User.userTimeZoneOptions());
            model.addAttribute("activeNav", "users");
            if (user.getUserGroupMap() != null) {
                List<Long> selectedGroupIds = new ArrayList<Long>();
                for (UserGroupMap map : user.getUserGroupMap()) {
                    selectedGroupIds.add(map.getGroup().getId());
                }
                user.setUserGroupId(selectedGroupIds);
            }

            if (user.getUserRoleMap() != null) {
                List<Long> selectedRoleIds = new ArrayList<Long>();
                for (UserRoleMap map : user.getUserRoleMap()) {
                    selectedRoleIds.add(map.getRole().getId());
                }
                user.setUserRoleId(selectedRoleIds);
            }
        }
        return new ModelAndView("dynamo-organization/usermanagement/edit-user");
    }

    /**
     * Redirects to the create-organization page.
     */
    @PreAuthorize("hasAnyAuthority('ROLE_SYSTEM_ADMIN')")
    @RequestMapping(value = "/createOrganization", method = RequestMethod.GET)
    public String createOrganization(Model model, HttpSession session) {

        logger.info("Entering createOrganization(): GET");

        CreateOrganizationDto createOrganizationDto = new CreateOrganizationDto();

        model.addAttribute("createOrganizationDto", createOrganizationDto);
        model.addAttribute("activeNav", "organizations");

        logger.info("Leaving createOrganization(): GET");
        return "dynamo-organization/usermanagement/create-organization";
    }

    /**
     * Creates a new organization with a default user.
     * 
     * @param createOrganizationDto
     * @param bindingResult
     * @param session
     * @param model
     * @param request
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ROLE_SYSTEM_ADMIN')")
    @RequestMapping(value = "/createOrganization", method = RequestMethod.POST)
    public ModelAndView createOrganization(
            @Valid @ModelAttribute("createOrganizationDto") CreateOrganizationDto createOrganizationDto,
            BindingResult bindingResult, HttpSession session, Model model, HttpServletRequest request) {
        logger.info("Entering createOrganization():POST");

        boolean hasErrors = false;

        // binding errors caught by java validation framework
        if (bindingResult.hasErrors()) {
            logger.info("There are binding result errors. # of field errors = {}. # of all errors = {}.",
                    bindingResult.getFieldErrors().size(), bindingResult.getAllErrors().size());
            for (FieldError fe : bindingResult.getFieldErrors()) {
                logger.info("Field error = " + fe.getField().toString() + ", " + fe.getDefaultMessage() + ", "
                        + fe.getCode());
            }
            hasErrors = true;
        }

        // return to the create organization screen if there are binding errors
        if (hasErrors == true) {
            logger.info("There are binding errors. Returning to create user screen");

            model.addAttribute("createOrganizationDto", createOrganizationDto);
            model.addAttribute("activeNav", "organizations");

            return new ModelAndView("dynamo-organization/usermanagement/create-organization");
        }
        // proceed with organization creation if there are no errors
        else {
            try {
                Organization organization = organizationService
                        .createOrganizationWithDefaultUser(createOrganizationDto);
                logger.info("Created organization with organization id = {}", organization.getId());
            } catch (DynamoDataAccessException e) {
                model.addAttribute("createOrganizationDto", createOrganizationDto);
                model.addAttribute("activeNav", "organizations");

                bindingResult.addError(new ObjectError("organization", e.getMessage()));
                return new ModelAndView("dynamo-organization/usermanagement/create-organization");
            }
        }

        // model.addAttribute("activeNav", " users");
        logger.info("Leaving createOrganization():POST");

        // return to the list of users
        return new ModelAndView("redirect:/organization/organizations");
    }
}
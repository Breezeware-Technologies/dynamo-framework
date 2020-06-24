package net.breezeware.dynamo.ldap.controller;

import java.util.ArrayList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import net.breezeware.dynamo.ldap.entity.Group;
import net.breezeware.dynamo.ldap.entity.User;
import net.breezeware.dynamo.ldap.service.api.LdapService;

/**
 * Controller methods for managing users and groups.
 *
 */
@Profile("LDAPAuthentication")
@Controller
@SessionAttributes("currentUser")
@RequestMapping(value = "/admin/usermanagement/*")
public class UserManagementController {

    Logger logger = LoggerFactory.getLogger(UserManagementController.class);

    @Autowired
    LdapService ldapService;

    @Value("${spring.ldap.base}")
    private String springLdapBase;

    /**
     * Redirects to the all-groups page displaying the list of groups.
     * 
     * @param model
     * @param predicate
     * @param pageable
     * @param parameters
     * @return
     */
    // @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public String listGroups(Model model) {
        logger.info("Entering listGroups()");

        List<Group> groups = ldapService.getGroups();

        model.addAttribute("groups", groups);
        model.addAttribute("activeNav", "groups");

        logger.info("Leaving listGroups(). # of groups fetched = {}.", groups.size());
        return "admin/usermanagement/all-groups";
    }

    /**
     * Redirects to the all-users page displaying the list of users.
     */
    // @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String listUsers(Model model) {
        logger.info("Entering listUsers()");

        List<User> users = ldapService.getUsers();

        model.addAttribute("users", users);
        model.addAttribute("activeNav", "users");

        logger.info("Leaving listUsers(). # of users fetched = {}", users.size());
        return "admin/usermanagement/all-users";
    }

    /**
     * Retrieves a single user and the groups associated with the user.
     */
    // @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/viewUser", method = RequestMethod.GET)
    public String viewUser(@RequestParam("uid") String uid, Model model) {
        logger.info("Entering viewUser(). Uid of user to fetch is {}", uid);

        if (uid != null && uid.trim().length() > 0) {
            // retrieve the user
            User user = ldapService.getUserByUid(uid);
            logger.info("user fetched = {}", user);

            // fetch groups associated with user
            List<Group> userGroups = new ArrayList<Group>();
            if (user != null) {
                String userDn = user.getDn().toString();
                logger.info("userDn = {}", userDn);

                // the DN value received does not contain the base value of LDAP, so updating
                // it.
                String updatedUserDn = userDn;
                if (springLdapBase != null && springLdapBase.trim().length() > 0) {
                    updatedUserDn = userDn.toLowerCase() + "," + springLdapBase;
                }
                logger.info("updatedDn = {}", updatedUserDn);

                userGroups = ldapService.getUserGroups(updatedUserDn);
                logger.info("# of groups fetched for user dn {} is {}.", user.getDn().toString(), userGroups.size());
            }

            model.addAttribute("user", user);
            model.addAttribute("userGroups", userGroups);
            model.addAttribute("activeNav", "users");
        } else {
            logger.error("UID is not provided. Could not fetch user.");
        }

        logger.info("Leaving viewUser().");
        return "admin/usermanagement/view-user";
    }
}
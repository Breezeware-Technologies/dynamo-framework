package net.breezeware.dynamo.organization.controller.api;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import net.breezeware.dynamo.organization.controller.OrganizationManagementController;
import net.breezeware.dynamo.organization.dto.UserDto;
import net.breezeware.dynamo.organization.entity.Group;
import net.breezeware.dynamo.organization.entity.User;
import net.breezeware.dynamo.organization.service.api.OrganizationService;
import net.breezeware.dynamo.util.DynamoAppBootstrapBean;
import net.breezeware.dynamo.util.mail.api.EmailService;
import net.breezeware.dynamo.util.usermgmt.CurrentUserDto;

/**
 * REST API Controller methods for roles, users and group services.
 */

@RestController
@RequestMapping(value = "/api/admin/organizationManagement/*")
@Profile("!service-layer-test")
public class OrganizationManagementRestController {

    Logger logger = LoggerFactory.getLogger(OrganizationManagementController.class);

    @Autowired
    OrganizationService organizationService;

    @Autowired
    EmailService emailService;

    // @Autowired
    // ServletRequest servletRequest;

    @Autowired
    DynamoAppBootstrapBean dynamoAppBootstrapBean;

    /**
     * Redirect to the all-groups page displaying the list of groups.
     * @param model     the holder for Model attributes
     * @param predicate the interface for Boolean typed expressions. Supports
     *                  binding of HTTP parameters to QueryDSL predicate
     * @param pageable  the interface for pagination information
     * @param session   the HTTPSession entity
     * @return a JSON string representing the list of groups
     */
    @RequestMapping(value = "/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Page<Group> listGroups(Model model, @QuerydslPredicate(root = Group.class) Predicate predicate,
            @PageableDefault(sort = { "name" }, page = 0, size = 12) Pageable pageable,
            @RequestParam MultiValueMap<String, String> parameters, HttpSession session) {

        logger.info("# of params = " + parameters.size());

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

        Page<Group> pagedGroups = organizationService.getGroups(organizationId, predicate, pageable);
        logger.info("# of groups fetched = {}", pagedGroups.getNumberOfElements());

        return pagedGroups;
    }

    /**
     * Retrieve a single user by email.
     * @param model   the holder for Model attributes
     * @param email   the email to uniquely identify a user
     * @param session the HTTPSession entity
     * @return a JSON string representing the user
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody User getUserByEmail(Model model, @RequestParam("email") String email, HttpSession session) {
        logger.info("Entering getUserByEmail(). Email = " + email);

        User user = organizationService.getUserByEmail(email);
        logger.info("Leaving getUserByEmail(). user = {}", user);

        return user;
    }

    /**
     * Retrieve a flat version (no nested properties) of the user entity.
     * @param email the email to uniquely identify a user
     * @return a JSON string representing the a flat version of the user entity
     */
    @RequestMapping(value = "/mobileUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody UserDto getUserByEmail(@Valid @RequestBody String email) {
        logger.info("Entering getUserByEmail(). Email = " + email);

        User user = organizationService.getUserByEmail(email);
        UserDto userDto = UserDto.createDto(user);

        logger.info("Leaving getUserByEmail(). userDto = {}", userDto);

        return userDto;
    }
}
package net.breezeware.dynamo.organization.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.breezeware.dynamo.organization.dto.UserRegistrationDto;
import net.breezeware.dynamo.organization.entity.User;
import net.breezeware.dynamo.organization.entity.UserRegistrationToken;
import net.breezeware.dynamo.organization.service.api.OrganizationService;
import net.breezeware.dynamo.util.exeption.DynamoDataAccessException;

/**
 * Controller methods for handling setting password after user-registration.
 * @author gowtham
 */
@Controller
public class UserRegistrationController {
    Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);

    @Autowired
    OrganizationService organizationService;

    public static final String USER_REGISTRATION_TYPE_RPM_PATIENT_ENROLLMENT = "rpmPatientEnrollment";

    @Value("${dynamo.encodePassword}")
    private boolean encodePassword;

    /**
     * Display a page for the user to set his initial password.
     * @param model   the holder for Model attributes
     * @param session the HTTPSession entity
     * @param token   user-registration token.
     * @return returns the string to user-registration template.
     */
    @RequestMapping(value = "/registerUser", method = RequestMethod.GET)
    public String registerUser(Model model, HttpSession session, @RequestParam("token") String token,
            @RequestParam(required = false) String registrationType) {
        logger.info("Entering registerUser Controller : GET. Token = {}", token);

        if (token != null && token.length() > 0) {

            // retrieve token based on token value param
            UserRegistrationToken userRegToken = organizationService.getUserRegistrationToken(token);

            // if token is valid, redirect to set password page
            if (userRegToken != null) {
                UserRegistrationDto dto = new UserRegistrationDto();
                dto.setRegistrationToken(token);
                model.addAttribute("userRegistrationDto", dto);
                model.addAttribute("registrationType", registrationType);
            } else {
                logger.error("Token for value {} does not exist in DB.", token);
            }
        } else {
            logger.error("Token for param value does not exist.");
        }

        logger.info("Exiting registerUser Controller : GET");
        return "user-registration";
    }

    /**
     * Complete user creation by setting the initial password for the user. User
     * will be automatically logged into the system after this step.
     * @param userRegistrationDto the DTO entity that holds the user registration
     *                            details.
     * @param bindingResult       the interface to represent binding results
     * @param model               the holder for Model attributes
     * @param session             the HTTPSession entity
     * @return returns to the 'login' mapping.
     */
    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public ModelAndView registerUser(@ModelAttribute("userRegistrationDto") UserRegistrationDto userRegistrationDto,
            BindingResult bindingResult, Model model, HttpSession session,
            @RequestParam(required = false) String registrationType) {

        logger.info("Entering registerUser Controller : POST. ,registrationType {}", registrationType);

        boolean hasErrors = false;

        if (userRegistrationDto.getPassword() == null || userRegistrationDto.getConfirmPassword() == null
                || userRegistrationDto.getPassword().trim().length() == 0
                || userRegistrationDto.getConfirmPassword().trim().length() == 0) {
            hasErrors = true;
            bindingResult.addError(new ObjectError("userRegistrationDto", "Passsword fields should not be empty"));
        } else if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getConfirmPassword())) {
            hasErrors = true;
            bindingResult.addError(new ObjectError("userRegistrationDto", "Password fields do not match!"));
        }

        if (hasErrors == true) {
            model.addAttribute("userRegistrationDto", userRegistrationDto);
            model.addAttribute("registrationType", registrationType);
            return new ModelAndView("user-registration");
        } else {
            String token = userRegistrationDto.getRegistrationToken();
            if (token != null) {
                UserRegistrationToken userRegToken = organizationService.getUserRegistrationToken(token);
                if (userRegToken != null) {
                    User user = userRegToken.getUser();
                    user.setStatus(User.STATUS_ACTIVE);
                    user.setPassword(userRegistrationDto.getPassword());
                    try {
                        organizationService.saveUser(user, encodePassword);
                    } catch (DynamoDataAccessException e) {
                        logger.error("Exception occured while updating the password for user.");

                    }
                }
            } else {

                logger.error("User registration token is Null.");
            }
        }
        if (registrationType != null && registrationType.length() > 0
                && registrationType.equalsIgnoreCase(USER_REGISTRATION_TYPE_RPM_PATIENT_ENROLLMENT)) {
            logger.info("Inside RPM Patient enrollment, registrationType {}", registrationType);
            // model.addAttribute("registrationType", registrationType);
            return new ModelAndView("patient-enrollment-complete-feedback");
        } else {
            logger.info("Exiting registerUser Controller : POST");
            // NOTE: return to application context root
            return new ModelAndView("redirect:/");
        }

    }
}
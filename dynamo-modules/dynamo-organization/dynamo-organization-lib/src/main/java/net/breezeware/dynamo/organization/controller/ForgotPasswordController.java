package net.breezeware.dynamo.organization.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.breezeware.dynamo.organization.dto.ForgotPasswordDto;
import net.breezeware.dynamo.organization.entity.PasswordResetToken;
import net.breezeware.dynamo.organization.entity.User;
import net.breezeware.dynamo.organization.service.api.OrganizationService;
import net.breezeware.dynamo.util.exeption.DynamoDataAccessException;
import net.breezeware.dynamo.util.mail.api.EmailService;

/**
 * 
 * Controller methods for forgot password case.
 */
@Controller
public class ForgotPasswordController {
    Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);

    @Autowired
    OrganizationService organizationService;

    @Autowired
    EmailService emailService;

//	@Autowired
//	DynamoConfigProperties dynamoConfigProperties;

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

    /**
     * 
     * Maps to the forgot-password page.
     * 
     * @param model
     * 
     * @return returns a string that maps to the 'forgot-password' template.
     */
    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public String forgotPassword(Model model) {
        ForgotPasswordDto forgotPassword = new ForgotPasswordDto();
        model.addAttribute("forgotPassword", forgotPassword);
        return "forgot-password";
    }

    /**
     * 
     * Gets the username and checks in database whether it exits.If so sends a email
     * containing a reset-password link(associated with a token),else redirects to
     * the 'reset-password-email-template' page.
     * 
     * @param forgotPassword populated DTO.
     * @param model
     * @param request
     * @return returns to a template/controller mapping.
     */
    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public ModelAndView forgotPassword(@Valid @ModelAttribute("forgotPassword") ForgotPasswordDto forgotPassword,
            Model model, HttpServletRequest request, BindingResult bindingResult) {
        logger.info("Entering forgotPassword Controller");

        boolean hasErrors = false;
        boolean mailSent = false;

        logger.debug("ForgotPassword = {}", forgotPassword);

        // get user by email
        User user = organizationService.getUserByEmail(forgotPassword.getEmail());

        // custom validation errors
        if (user == null) {
            bindingResult.addError(new ObjectError("user", "Email not found."));
            hasErrors = true;
        }

        // return to the create user screen if there are binding errors
        if (hasErrors == true) {
            logger.info("There are binding errors. Returning to forgot-password screen.");
            model.addAttribute("forgotPassword", forgotPassword);
            return new ModelAndView("forgot-password");
        } else {
            PasswordResetToken passwordResetToken = organizationService.createPasswordResetToken(user);

            // set attributes to be used in Email template sent to the user.
            Map<String, String> keyVals = new HashMap<String, String>();
            keyVals.put("applicationName", applicationName);
            keyVals.put("applicationOwner", applicationOwner);
            keyVals.put("firstName", passwordResetToken.getUser().getFirstName());
            keyVals.put("lastName", passwordResetToken.getUser().getLastName());

            // get server address/host name and port for current deployment
            StringBuffer url = request.getRequestURL();
            String uri = request.getRequestURI();
            String host = url.substring(0, url.indexOf(uri)); // result

            logger.info("url = {}, uri = {}", url.toString(), uri);
            logger.info("host = {}", host);

            String serverAddress = applicationServerUrl;

            keyVals.put("resetPasswordLink", serverAddress + "/resetPassword?token=" + passwordResetToken.getToken());
            String templateName = "reset-password-email-template";
            logger.info("Sending email with template name = {}", templateName);

            String emailSubject = applicationName + " - Reset Password";
            emailService.sendMail(applicationAdminEmail, user.getEmail(), emailSubject, keyVals, templateName);

            logger.info("Sent email!");
            mailSent = true;
            model.addAttribute("mailSent", mailSent);
            return new ModelAndView("/login");
        }
    }

    /**
     * 
     * Gets the token from the email link and redirects to the 'reset-password'
     * page.
     * 
     * @param model
     * @param session
     * @param token   password-reset token.
     * @return returns the string to the reset-password template.
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public String resetPassword(Model model, HttpSession session, @RequestParam("token") String token) {

        logger.info("Entering resetPassword Controller : GET");
        if (token != null && token.length() > 0) {

            // retrieve token based on token value param
            PasswordResetToken pRToken = organizationService.getPasswordResetToken(token);

            // if token is valid, redirect to set password page
            if (pRToken != null) {
                ForgotPasswordDto dto = new ForgotPasswordDto();
                dto.setToken(token);
                model.addAttribute("passwordDto", dto);
            } else {
                logger.error("Token for value {} does not exist in DB.", token);
            }
        } else {
            logger.error("Token for param value does not exist.");
        }

        logger.info("Exiting resetPassword Controller : GET");
        return "reset-password";
    }

    /**
     * 
     * Gets the password and persists the changed password associated with the user
     * in the database.
     * 
     * @param passwordDto   populated DTO.
     * @param bindingResult
     * @param model
     * @param session
     * @return returns to the 'login' controller mapping.
     * @throws DynamoDataAccessException
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ModelAndView resetPassword(@ModelAttribute("passwordDto") ForgotPasswordDto passwordDto,
            BindingResult bindingResult, Model model, HttpSession session, RedirectAttributes redir)
            throws DynamoDataAccessException {
        logger.info("Entering resetPassword Controller : POST. passwordDto = {}", passwordDto);

        boolean hasErrors = false;

        if (passwordDto.getPassword() == null || passwordDto.getConfirmPassword() == null
                || passwordDto.getPassword().trim().length() == 0
                || passwordDto.getConfirmPassword().trim().length() == 0) {
            hasErrors = true;
            bindingResult.addError(new ObjectError("passwordDto", "Passsword fields should not be empty"));
        }

//		else if (!checkPasswordStrength(passwordDto.getPassword())) {
//			hasErrors = true;
//			bindingResult.addError(new ObjectError("passwordDto",
//					"Please enter a strong password. Minumum 8 characters with a numeric, a upper case alphabet and a special character"));
//		} 

        else if (!passwordDto.getPassword().equals(passwordDto.getConfirmPassword())) {
            hasErrors = true;
            bindingResult.addError(new ObjectError("passwordDto", "Password fields do not match!"));
        }

        if (hasErrors == true) {
            model.addAttribute("passwordDto", passwordDto);
            return new ModelAndView("reset-password");
        } else {
            String token = passwordDto.getToken();
            if (token != null) {
                PasswordResetToken pRToken = organizationService.getPasswordResetToken(token);
                if (pRToken != null) {
                    User user = pRToken.getUser();
                    if (user.getStatus().equalsIgnoreCase(User.STATUS_NEW)) {
                        user.setStatus(User.STATUS_ACTIVE);
                    }
                    user.setPassword(passwordDto.getPassword());
                    try {
                        organizationService.saveUser(user, encodePassword);
                        redir.addFlashAttribute("successMessage",
                                "You have successfully reset your password.  You may now login.");
                    } catch (DynamoDataAccessException e) {
                        logger.error("Exception occured while updating the password for user.");

                    }
                }
            } else {

                logger.error("User registration token is Null.");
            }
        }

        logger.info("Exiting resetPassword Controller : POST");

        return new ModelAndView("redirect:/");
    }

    private boolean checkPasswordStrength(String password) {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        return password.matches(pattern);
    }

}
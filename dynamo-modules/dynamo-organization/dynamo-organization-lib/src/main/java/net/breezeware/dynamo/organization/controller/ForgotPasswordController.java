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
 * Controller methods for forgot password case.
 */
@Controller
public class ForgotPasswordController {
    Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);

    @Autowired
    OrganizationService organizationService;

    @Autowired
    EmailService emailService;

    public static final String SOURCE_OF_DATA_FROM_MOBILE = "mobile";

    // @Autowired
    // DynamoConfigProperties dynamoConfigProperties;

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
     * Redirect the user to the forgot-password page.
     * @param model the holder for Model attributes
     * @source the source that initiated this request. It is used to redirect the
     *         user to appropriate page after the password is reset. Valid value is
     *         'mobile'
     * @return returns a string that maps to the 'forgot-password' template.
     */
    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public String forgotPassword(Model model, @RequestParam(required = false) String source) {
        ForgotPasswordDto forgotPassword = new ForgotPasswordDto();
        model.addAttribute("forgotPassword", forgotPassword);
        model.addAttribute("email", forgotPassword.getEmail());
        model.addAttribute("source", source);
        return "forgot-password";
    }

    /**
     * Get the username and check in database whether it exits. If so send an email
     * containing a reset-password link (associated with a token), else, redirect to
     * the 'reset-password-email-template' page.
     * @param forgotPassword the DTP populated using the form on the UI
     * @param source         the source that initiated this request. It is used to
     *                       redirect the user to appropriate page after the
     *                       password is reset. Valid value is 'mobile'
     * @param email          the email to identify the user whose password will be
     *                       reset
     * @param model          the holder for Model attributes
     * @param request        the HTTPServeletRequest
     * @param bindingResult  the interface to represent binding results
     * @return
     */
    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public ModelAndView forgotPassword(@Valid @ModelAttribute("forgotPassword") ForgotPasswordDto forgotPassword,
            @RequestParam(required = false) String source, @RequestParam(required = false) String email, Model model,
            HttpServletRequest request, BindingResult bindingResult) {
        logger.info("Entering forgotPassword Controller email={}", email);

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
            model.addAttribute("source", source);
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

            keyVals.put("resetPasswordLink",
                    serverAddress + "/resetPassword?token=" + passwordResetToken.getToken() + "&source=" + source);
            String templateName = "reset-password-email-template";
            logger.info("Sending email with template name = {}", templateName);

            String emailSubject = applicationName + " - Reset Password";
            emailService.sendMail(applicationAdminEmail, user.getEmail(), emailSubject, keyVals, templateName);

            logger.info("Sent email!");
            mailSent = true;

            if (source != null && source.equals(SOURCE_OF_DATA_FROM_MOBILE)) {
                logger.info("Enter with the source param");
                model.addAttribute("email", email);
                return new ModelAndView("forgot-password-feedback");
            } else {
                model.addAttribute("mailSent", mailSent);
                return new ModelAndView("/login");
            }

        }
    }

    /**
     * Get the token from the email link and redirect to the 'reset-password' page.
     * @param model   the holder for Model attributes
     * @param session the HTTPSession entity
     * @param token   the request param required to identify a specific password
     *                reset request
     * @param source  the source that initiated this request. It is used to redirect
     *                the user to appropriate page after the password is reset.
     *                Valid value is 'mobile'
     * @return a string that maps to the UI Thymeleaf template
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public String resetPassword(Model model, HttpSession session, @RequestParam("token") String token,
            @RequestParam(required = false) String source) {

        logger.info("Entering resetPassword Controller : GET,source={}", source);
        if (token != null && token.length() > 0) {

            // retrieve token based on token value param
            PasswordResetToken prToken = organizationService.getPasswordResetToken(token);

            // if token is valid, redirect to set password page
            if (prToken != null) {
                ForgotPasswordDto dto = new ForgotPasswordDto();
                dto.setToken(token);
                model.addAttribute("passwordDto", dto);
                model.addAttribute("source", source);
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
     * Update the password associated with the user in the database.
     * @param passwordDto   the DTO populated in form that contains the password
     *                      entered by the user
     * @param source        the source that initiated this request. It is used to
     *                      redirect the user to appropriate page after the password
     *                      is reset. Valid value is 'mobile'
     * @param bindingResult the interface to represent binding results
     * @param model         the holder for Model attributes
     * @param redir         the RedirectAttributes entity
     * @return returns to the 'login' controller mapping.
     * @throws DynamoDataAccessException exception thrown when there is an error
     *                                   while resetting the password
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ModelAndView resetPassword(@ModelAttribute("passwordDto") ForgotPasswordDto passwordDto,
            @RequestParam(required = false) String source, BindingResult bindingResult, Model model,
            RedirectAttributes redir) throws DynamoDataAccessException {
        logger.info("Entering resetPassword Controller : POST. passwordDto = {} source = {}", passwordDto, source);

        boolean hasErrors = false;

        if (passwordDto.getPassword() == null || passwordDto.getConfirmPassword() == null
                || passwordDto.getPassword().trim().length() == 0
                || passwordDto.getConfirmPassword().trim().length() == 0) {
            hasErrors = true;
            bindingResult.addError(new ObjectError("passwordDto", "Passsword fields should not be empty"));
        } else if (!passwordDto.getPassword().equals(passwordDto.getConfirmPassword())) {
            hasErrors = true;
            bindingResult.addError(new ObjectError("passwordDto", "Password fields do not match!"));
        }
        // else if (!checkPasswordStrength(passwordDto.getPassword())) {
        // hasErrors = true;
        // bindingResult.addError(new ObjectError("passwordDto",
        // "Please enter a strong password. Minumum 8 characters with a numeric, a upper
        // case alphabet and a special character"));
        // }

        if (hasErrors == true) {
            model.addAttribute("passwordDto", passwordDto);
            model.addAttribute("source", source);
            return new ModelAndView("reset-password");
        } else {
            String token = passwordDto.getToken();
            if (token != null) {
                PasswordResetToken prToken = organizationService.getPasswordResetToken(token);
                if (prToken != null) {
                    User user = prToken.getUser();
                    if (user.getStatus().equalsIgnoreCase(User.STATUS_NEW)) {
                        user.setStatus(User.STATUS_ACTIVE);
                    }
                    user.setPassword(passwordDto.getPassword());
                    try {
                        organizationService.saveUser(user, encodePassword);
                        if (source != null && source.equals(SOURCE_OF_DATA_FROM_MOBILE)) {
                            logger.info("Enter with the source param");
                            return new ModelAndView("reset-password-feedback");
                        }
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

    // private boolean checkPasswordStrength(String password) {
    // String pattern =
    // "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
    // return password.matches(pattern);
    // }
}
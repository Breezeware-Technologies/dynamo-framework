package net.breezeware.dynamo.auth.springsecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.breezeware.dynamo.auth.DynamoAuthConfigProperties;

@Service
public class DynamoUserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Value("${dynamo.encodePassword}")
    private boolean encodePassword;

    Logger logger = LoggerFactory.getLogger(DynamoUserDetailsAuthenticationProvider.class);

    /*
     * NOTE: dynamoUserDetailsService is declared as an interface type
     * 'UserDetailsService' instead of 'DynamoUserDetailsService' in order to
     * address the issue mentioned below (Description & Action). This issue was
     * encountered when the 'DynamoUserDetailsAuthenticationProvider' class was used
     * in a Tulasi Carejoy project. Description: The bean 'dynamoUserDetailsService'
     * could not be injected as a
     * 'net.breezeware.dynamo.auth.springsecurity.DynamoUserDetailsService' because
     * it is a JDK dynamic proxy that implements:
     * org.springframework.security.core.userdetails.UserDetailsService Action:
     * Consider injecting the bean as one of its interfaces or forcing the use of
     * CGLib-based proxies by setting proxyTargetClass=true on @EnableAsync
     * and/or @EnableCaching. (LoggingFailureAnalysisReporter.java, line 42)
     */
    @Autowired
    DynamoUserDetailsService dynamoUserDetailsService;

    @Autowired
    private PasswordEncoder userPasswordEncoder;

    @Autowired
    private DynamoAuthConfigProperties dynamoAuthConfigProperties;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // TODO Auto-generated method stub

    }

    @Override
    protected UserDetails retrieveUser(String email, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {

        logger.info("Entering retrieveUser() username = '{}'. authentication = '{}'.", email, authentication);

        if (authentication != null) {

            UserDetails ud = dynamoUserDetailsService.loadUserByUsername(authentication.getName());
            logger.info("UserDetails from DB: username = {}, password = {}", ud.getUsername(), ud.getPassword());

            logger.info("authenticationPasswordEncoding = {}", dynamoAuthConfigProperties.getPasswordEncoding());

            if (ud != null && authentication.getCredentials() != null) {

                // encode the password according to the property set by user
                String userEnteredUsername = authentication.getName();
                String userEnteredPassword = authentication.getCredentials().toString();
                logger.info("User entered username = {}, password = {}", userEnteredUsername, userEnteredPassword);

                logger.info("Username matched = {}", ud.getUsername().equalsIgnoreCase(userEnteredUsername));
                logger.info("Password matched = {}",
                        new BCryptPasswordEncoder().matches(userEnteredPassword, ud.getPassword()));

                if (ud.getUsername().equalsIgnoreCase(userEnteredUsername) && encodePassword == true
                        && new BCryptPasswordEncoder().matches(userEnteredPassword, ud.getPassword())) {
                    logger.info("Leaving retrieveUser(). encodePassword = {}. Credentials matched!", encodePassword);
                    return ud;
                } else if (ud.getUsername().equalsIgnoreCase(userEnteredUsername) && encodePassword == false
                        && userEnteredPassword.equalsIgnoreCase(ud.getPassword())) {
                    logger.info("Leaving retrieveUser(). encodePassword = {}. Credentials matched!", encodePassword);
                    return ud;
                } else {
                    logger.error("Username & password in authentication token does not match details in system. "
                            + "Throwing Exception.");
                    throw new BadCredentialsException(
                            "Username & password in authentication token does not match details in system.");
                }
            } else {
                logger.error("User details could not be found for username. Throwing Exception.");
                throw new BadCredentialsException("User details could not be found for username.");
            }
        } else {
            logger.error("Authentication received is Null. Cannot proceed with Authentication.");
            throw new BadCredentialsException("Authentication is NULL.");
        }
    }
}
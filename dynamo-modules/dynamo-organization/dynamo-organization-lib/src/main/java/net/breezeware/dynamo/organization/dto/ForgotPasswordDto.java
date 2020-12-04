package net.breezeware.dynamo.organization.dto;

import lombok.Data;

/**
 * Class used for resetting the password in case the user forgets his/her
 * password.
 */
@Data
public class ForgotPasswordDto {

    /**
     * Username used for retrieving the user during the forgot password case.
     */
    String email;

    /**
     * Application generated token that will be sent to the user via email. User
     * will need this token to successfully change their password.
     */
    String token;

    /**
     * Password set by the user.
     */
    private String password;

    /**
     * Confirm password should be the same as the password field above.
     */
    private String confirmPassword;
}

package net.breezeware.dynamo.organization.dto;

import com.google.gson.Gson;

/**
 * Class used for resetting the password in case the user forgets his/her
 * password.
 */
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}

package net.breezeware.dynamo.organization.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

/**
 * Class is used during the user registration process. New users can set their
 * initial passwords to gain access to the application.
 */
@XmlRootElement
public class UserRegistrationDto implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Application generated token that will be sent to the user via email. User
     * will need this token to successfully set their initial password.
     */
    @Expose
    private String registrationToken;

    /**
     * Password set by the user
     */
    @Expose
    private String password;

    /**
     * Confirm password should be the same as the password field above.
     */
    @Expose
    private String confirmPassword;

    public String getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
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
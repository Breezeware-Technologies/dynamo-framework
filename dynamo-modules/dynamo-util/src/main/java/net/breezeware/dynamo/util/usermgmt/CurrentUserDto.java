package net.breezeware.dynamo.util.usermgmt;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO to contain minimal information about a User who is currently logged into
 * the application.
 */
public class CurrentUserDto {

    /**
     * ID of user.
     */
    @Getter
    @Setter
    private long userId;

    /**
     * ID of user defined organization to which this user is associated with.
     */
    @Getter
    @Setter
    private String organizationId;

    /**
     * Email, typically used to login to the application.
     */
    @Getter
    @Setter
    private String email;

    /**
     * Full name for this user (First Name Middle Initial(Optional) LastName).
     */
    @Getter
    @Setter
    private String fullName;

    /**
     * Roles assigned to this user presented as a list of comma separated values.
     */
    @Getter
    @Setter
    private String rolesCsv;

    /**
     * Time zone ID associated with the user. Example: America/New_York,
     * Asia/Kolkata
     */
    @Getter
    @Setter
    private String userTimeZoneId;

    /**
     * Non-empty constructor.
     * @param organizationId organization ID
     * @param email          user email
     * @param fullName       user's full name
     * @param rolesCsv       user's roles as a CSV string
     * @param userTimeZoneId user's time zone
     */
    public CurrentUserDto(String organizationId, String email, String fullName, String rolesCsv,
            String userTimeZoneId) {
        this.email = email;
        this.organizationId = organizationId;
        this.fullName = fullName;
        this.rolesCsv = rolesCsv;
        this.userTimeZoneId = userTimeZoneId;
    }

    /**
     * Non-empty constructor.
     * @param userId         user ID
     * @param organizationId organization ID
     * @param email          user email
     * @param fullName       user's full name
     * @param rolesCsv       user's roles as a CSV string
     * @param userTimeZoneId user's time zone
     */
    public CurrentUserDto(long userId, String organizationId, String email, String fullName, String rolesCsv,
            String userTimeZoneId) {
        this.userId = userId;
        this.email = email;
        this.organizationId = organizationId;
        this.fullName = fullName;
        this.rolesCsv = rolesCsv;
        this.userTimeZoneId = userTimeZoneId;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
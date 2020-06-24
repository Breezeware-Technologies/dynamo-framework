package net.breezeware.dynamo.util.usermgmt;

import com.google.gson.Gson;

/**
 * DTO to contain minimal information about a User who is currently logged into
 * the application
 * 
 */
public class CurrentUserDto {

    /**
     * ID of user.
     */
    private long userId;

    /**
     * ID of user defined organization to which this user is associated with.
     */
    private String organizationId;

    /**
     * Email, typically used to login to the application.
     */
    private String email;

    /**
     * Full name for this user (First Name <space> Middle Initial(Optional) <space>
     * LastName).
     */
    private String fullName;

    /**
     * Roles assigned to this user presented as a list of comma separated values.
     */
    private String rolesCsv;

    /**
     * Time zone ID associated with the user. Example: America/New_York,
     * Asia/Kolkata
     */
    private String userTimeZoneId;

    public CurrentUserDto() {
    }

    public CurrentUserDto(String organizationId, String email, String fullName, String rolesCsv,
            String userTimeZoneId) {
        this.email = email;
        this.organizationId = organizationId;
        this.fullName = fullName;
        this.rolesCsv = rolesCsv;
        this.userTimeZoneId = userTimeZoneId;
    }

    public CurrentUserDto(long userId, String organizationId, String email, String fullName, String rolesCsv,
            String userTimeZoneId) {
        this.userId = userId;
        this.email = email;
        this.organizationId = organizationId;
        this.fullName = fullName;
        this.rolesCsv = rolesCsv;
        this.userTimeZoneId = userTimeZoneId;
    }
    
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRolesCsv() {
        return rolesCsv;
    }

    public String getUserTimeZoneId() {
        return userTimeZoneId;
    }

    public void setUserTimeZoneId(String userTimeZoneId) {
        this.userTimeZoneId = userTimeZoneId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setRolesCsv(String rolesCsv) {
        this.rolesCsv = rolesCsv;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
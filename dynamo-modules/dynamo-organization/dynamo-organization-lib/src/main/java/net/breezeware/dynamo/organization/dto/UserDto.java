package net.breezeware.dynamo.organization.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import net.breezeware.dynamo.organization.entity.User;
import net.breezeware.dynamo.organization.entity.UserGroupMap;
import net.breezeware.dynamo.organization.entity.UserRoleMap;

@XmlRootElement
public class UserDto implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Expose
    private long id;

    @Expose
    private String username;

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private String middleInitial;

    @Expose
    private Calendar createdDate;

    @Expose
    private Calendar modifiedDate;

    @Expose
    private long organizationId;

    @Expose
    private String organizationName;

    @Expose
    private String organizationDescription;

    @Expose
    private List<String> userRoles;

    @Expose
    private List<String> userGroups;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    public Calendar getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Calendar modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationDescription() {
        return organizationDescription;
    }

    public void setOrganizationDescription(String organizationDescription) {
        this.organizationDescription = organizationDescription;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<String> userRoles) {
        this.userRoles = userRoles;
    }

    public List<String> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<String> userGroups) {
        this.userGroups = userGroups;
    }

    /**
     * Create a DTO object from User entity.
     * 
     * @param user
     * @return
     */
    public static UserDto createDto(User user) {
        UserDto dto = new UserDto();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setMiddleInitial(user.getMiddleInitial());
        dto.setCreatedDate(user.getCreatedDate());
        dto.setModifiedDate(user.getModifiedDate());

        if (user.getOrganization() != null) {
            dto.setOrganizationId(user.getOrganization().getId());
            dto.setOrganizationName(user.getOrganization().getName());
            dto.setOrganizationDescription(user.getOrganization().getDescription());
        }

        if (user.getUserGroupMap() != null) {
            List<String> groups = new ArrayList<String>();
            for (UserGroupMap map : user.getUserGroupMap()) {
                groups.add(map.getGroup().getName());
            }
            dto.setUserGroups(groups);
        }

        if (user.getUserRoleMap() != null) {
            List<String> roles = new ArrayList<String>();
            for (UserRoleMap map : user.getUserRoleMap()) {
                roles.add(map.getRole().getName());
            }
            dto.setUserGroups(roles);
        }

        return dto;
    }

    public String toString() {
        return new Gson().toJson(this);
    }

}
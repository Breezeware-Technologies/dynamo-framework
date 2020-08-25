package net.breezeware.dynamo.organization.dto;

import java.util.Calendar;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.User;

@XmlRootElement
public class CreateOrganizationDto implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Expose
    @Size(min = 1, max = 45, message = "{Size.createOrganizationDto.organizationName}")
    @Pattern(regexp = "^(?=[A-Za-z]).*[\\s_A-Za-z\\d]{6,}$", message = "{Pattern.organization.name}")
    private String organizationName;

    @Expose
    @Size(min = 1, max = 180, message = "{Size.createorganizationdto.organizationDescription}")
    private String organizationDescription;

    @Expose
    @Size(min = 1, max = 45, message = "{Size.user.firstName}")
    private String defaultUserFirstName;

    @Expose
    @Size(min = 1, max = 45, message = "{Size.user.lastName}")
    private String defaultUserLastName;

    @Expose
    @Size(min = 0, max = 1, message = "{Size.user.middleInitial}")
    private String defaultUserMiddleInitial;

    @Expose
    @Size(min = 1, max = 45, message = "{Size.user.email}")
    @Pattern(regexp = ".+@.+\\..+", message = "{Pattern.user.email}")
    private String defaultUserEmail;

    @Expose
    private Calendar createdDate;

    @Expose
    private Calendar modifiedDate;

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

    public String getDefaultUserFirstName() {
        return defaultUserFirstName;
    }

    public void setDefaultUserFirstName(String defaultUserFirstName) {
        this.defaultUserFirstName = defaultUserFirstName;
    }

    public String getDefaultUserLastName() {
        return defaultUserLastName;
    }

    public void setDefaultUserLastName(String defaultUserLastName) {
        this.defaultUserLastName = defaultUserLastName;
    }

    public String getDefaultUserMiddleInitial() {
        return defaultUserMiddleInitial;
    }

    public void setDefaultUserMiddleInitial(String defaultUserMiddleInitial) {
        this.defaultUserMiddleInitial = defaultUserMiddleInitial;
    }

    public String getDefaultUserEmail() {
        return defaultUserEmail;
    }

    public void setDefaultUserEmail(String defaultUserEmail) {
        this.defaultUserEmail = defaultUserEmail;
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

    public String toString() {
        return new Gson().toJson(this);
    }

    public static Organization createOrganizationFromDto(CreateOrganizationDto dto) {
        Organization organization = new Organization();
        organization.setName(dto.getOrganizationName());
        organization.setDescription(dto.getOrganizationDescription());
        organization.setCreatedDate(Calendar.getInstance());
        organization.setModifiedDate(Calendar.getInstance());

        return organization;
    }

    public static User createUserFromDto(Organization organization, CreateOrganizationDto dto) {
        User user = new User();

        user.setOrganization(organization);
        user.setCreatedDate(Calendar.getInstance());
        user.setEmail(dto.getDefaultUserEmail());
        user.setFirstName(dto.getDefaultUserFirstName());
        user.setLastName(dto.getDefaultUserLastName());
        user.setMiddleInitial(dto.getDefaultUserMiddleInitial());
        user.setModifiedDate(Calendar.getInstance());
        user.setStatus(User.STATUS_NEW);
        user.setUserUniqueId(User.generateUniqueId());

        return user;
    }
}
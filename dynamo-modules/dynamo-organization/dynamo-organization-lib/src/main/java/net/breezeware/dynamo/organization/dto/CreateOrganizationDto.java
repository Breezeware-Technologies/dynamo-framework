package net.breezeware.dynamo.organization.dto;

import java.util.Calendar;
import java.util.Optional;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

import net.breezeware.dynamo.organization.entity.Address;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.User;

import lombok.Data;

@XmlRootElement
@Data
public class CreateOrganizationDto implements java.io.Serializable {

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
    private String addressLine1;

    @Expose
    private String addressLine2;

    @Expose
    private String city;

    @Expose
    private String state;

    /**
     * ZIP code in an address. This is also called postal code in certain countries.
     */
    @Expose
    private String zipCode;

    @Expose
    private Calendar createdDate;

    @Expose
    private Calendar modifiedDate;

    /**
     * Create an Organization entity from the CreateOrganizationDto entity.
     * @param dto the CreateOrganizationDto
     * @return an Organization entity
     */
    public static Organization createOrganizationFromDto(CreateOrganizationDto dto) {
        Organization organization = new Organization();
        organization.setName(dto.getOrganizationName());
        organization.setDescription(dto.getOrganizationDescription());
        organization.setCreatedDate(Calendar.getInstance());
        organization.setModifiedDate(Calendar.getInstance());

        return organization;
    }

    /**
     * Create an User entity from the CreateOrganizationDto entity and the
     * Organization entity.
     * @param organization an Organization entity
     * @param dto          the CreateOrganizationDto entity
     * @return a User entity
     */
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

    /**
     * Create Address entity from CreateOrganizationDto entity
     * @param dto the CreateOrganizationDto
     * @return Address entity
     */
    public static Optional<Address> createAddressFromDto(CreateOrganizationDto dto) {

        Optional<Address> addrOpt = Optional.empty();

        // create a valid address entity only if the city value is present
        if (dto != null && dto.getCity() != null && dto.getCity().trim().length() > 0) {
            Address organizationAddress = new Address();
            organizationAddress.setAddressLine1(dto.getAddressLine1());
            organizationAddress.setAddressLine2(dto.getAddressLine2());
            organizationAddress.setCity(dto.getCity());
            organizationAddress.setState(dto.getState());
            organizationAddress.setPincode(dto.getPincode());

            addrOpt = Optional.of(organizationAddress);
        }

        return addrOpt;
    }
}
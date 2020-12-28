package net.breezeware.dynamo.organization.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;
// import org.hibernate.envers.Audited;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.breezeware.dynamo.util.string.RandomString;

@XmlRootElement
@Entity
@ToString
// @Audited
@Table(name = "dynamo_user", schema = "dynamo")
public class User implements Serializable {
    /**
     * A Class for users.
     * @author gowtham
     */
    private static final long serialVersionUID = 1L;

    public static String STATUS_NEW = "NEW";
    public static String STATUS_ACTIVE = "ACTIVE";
    public static String STATUS_INACTIVE = "INACTIVE";

    /**
     * Unique key to identify a user, auto-generate value.
     */

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    @SequenceGenerator(name = "seq_gen", sequenceName = "user_seq", schema = "dynamo", allocationSize = 1)
    @Getter
    @Setter
    private long id;

    /**
     * Unique ID assigned to a user by the application.
     */

    @Size(min = 1, max = 90, message = "{Size.user.userUniqueId}")
    @Getter
    @Setter
    @Column(name = "user_unique_id", length = 90)
    private String userUniqueId;

    public void setUserRoleMap(List<UserRoleMap> userRoleMap) {
        this.userRoleMap = userRoleMap;
    }

    public void setUserGroupMap(List<UserGroupMap> userGroupMap) {
        this.userGroupMap = userGroupMap;
    }

    /**
     * Defines the type of user organization.
     */

    @OneToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    @Getter
    @Setter
    private Organization organization;

    @Size(min = 6, max = 45, message = "{Size.user.email}")
    @Pattern(regexp = ".+@.+\\..+", message = "{Pattern.user.email}")
    @Getter
    @Setter
    @Column(name = "email", length = 90)
    private String email;

    @Column(name = "phone_number", length = 30)
    @Getter
    @Setter
    private String phoneNumber;

    /**
     * Password protection for the user organization.
     */

    @Size(min = 1, max = 90, message = "{Size.user.password}")
    @Column(name = "password", length = 90)
    @Getter
    @Setter
    private String password;

    /**
     * Status of the user entity.
     */

    @Column(name = "status", length = 45)
    @Getter
    @Setter
    private String status;

    /**
     * User Time Zone ID (eg., America/New_York, Asia/Kolkata)
     */

    @Size(min = 1, max = 90, message = "{Size.user.userTimeZoneId}")
    @Column(name = "user_time_zone_id", length = 90)
    @Getter
    @Setter
    private String userTimeZoneId;

    @Size(min = 1, max = 45, message = "{Size.user.firstName}")
    @Column(name = "first_name", length = 45)
    @Getter
    @Setter
    private String firstName;

    @Size(min = 1, max = 45, message = "{Size.user.lastName}")
    @Column(name = "last_name", length = 45)
    @Getter
    @Setter
    private String lastName;

    @Size(min = 0, max = 1, message = "{Size.user.middleInitial}")
    @Column(name = "middle_initial", length = 1)
    @Getter
    @Setter
    private String middleInitial;

    /**
     * User's date of creation.
     */

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "created_date")
    @Getter
    @Setter
    private Calendar createdDate;

    /**
     * Last modified date.
     */

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "modified_date")
    @Getter
    @Setter
    private Calendar modifiedDate;

    /**
     * User is mapped to one to more roles. One-to-many relationship.
     */

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @Fetch(value = FetchMode.SUBSELECT)
    @Getter
    @Setter
    private List<UserRoleMap> userRoleMap;

    /**
     * Used for capturing the list of User-Group mapping ID.
     */

    @Transient
    @Getter
    @Setter
    private List<Long> userGroupId;

    /**
     * User is mapped to one or more groups. One-to-many relationship.
     */

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @Fetch(value = FetchMode.SUBSELECT)
    @Getter
    @Setter
    private List<UserGroupMap> userGroupMap;

    /**
     * Used for capturing the list of User-Role mapping ID.
     */

    @Transient
    @Getter
    @Setter
    private List<Long> userRoleId;

    /**
     * Comma Separated Value (CSV) string of all roles assigned to the user.
     * Primarily used for display purposes and is not stored in the DB.
     */

    @Transient
    @Getter
    @Setter
    private String userRolesCsv;

    /**
     * Comma Separated Value (CSV) string of all groups assigned to the user.
     * Primarily used for display purposes and is not stored in the DB.
     */

    @Transient
    @Getter
    @Setter
    private String userGroupsCsv;

    public static String generateUniqueId() {
        RandomString rs = new RandomString(20);
        return rs.nextString();
    }

    /**
     * Retrieve a list of TimeZone options.
     * @return a list of time zones
     */
    public static List<String> userTimeZoneOptions() {
        List<String> ts = new ArrayList<String>();

        ts.add("");
        ts.add("America/Anchorage");
        ts.add("America/Vancouver");
        ts.add("America/Los_Angeles");
        ts.add("America/Tijuana");
        ts.add("America/Edmonton");
        ts.add("America/Denver");
        ts.add("America/Phoenix");
        ts.add("America/Mazatlan");
        ts.add("America/Winnipeg");
        ts.add("America/Regina");
        ts.add("America/Chicago");
        ts.add("America/Mexico_City");
        ts.add("America/Guatemala");
        ts.add("America/El_Salvador");
        ts.add("America/Managua");
        ts.add("America/Costa_Rica");
        ts.add("America/Montreal");
        ts.add("America/New_York");
        ts.add("America/Indianapolis");
        ts.add("America/Panama");
        ts.add("America/Bogota");
        ts.add("America/Lima");
        ts.add("America/Halifax");
        ts.add("America/Puerto_Rico");
        ts.add("America/Caracas");
        ts.add("America/Santiago");
        ts.add("America/St_Johns");
        ts.add("America/Montevideo");
        ts.add("America/Araguaina");
        ts.add("America/Argentina/Buenos_Aires");
        ts.add("America/Godthab");
        ts.add("America/Sao_Paulo");
        ts.add("Pacific/Midway");
        ts.add("Pacific/Pago_Pago");
        ts.add("Pacific/Honolulu");
        ts.add("Asia/Kolkata");

        return ts;
    }

}
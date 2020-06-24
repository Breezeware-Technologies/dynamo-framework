package net.breezeware.dynamo.organization.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

//import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

/**
 * A Class to identify roles of users.
 * 
 * @author gowtham
 */
@XmlRootElement
@Entity
// @Audited
@Table(name = "role", schema = "dynamo")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String USER_ROLE_SYSTEM_ADMIN = "ROLE_SYSTEM_ADMIN";
    public static final String USER_ROLE_ORGANIZATION_ADMIN = "ORGANIZATION_ADMIN";

    /**
     * Unique key to identify a role,auto-generated value.
     */
    @Expose
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    @SequenceGenerator(name = "seq_gen", sequenceName = "role_seq", schema = "dynamo", allocationSize = 1)
    private long id;

    /**
     * Refers to the role name,unique.
     */
    @Expose
    @Size(min = 1, max = 45, message = "{Size.role.name}")
    @Column(name = "name", length = 45)
    @Pattern(regexp = "^(?=[A-Za-z]).*[\\s_A-Za-z\\d]{6,}$", message = "{Pattern.role.name}")
    private String name;

    /**
     * Detailed description of the role name.
     */
    @Expose
    @Size(min = 1, max = 180, message = "{Size.role.description}")
    @Column(name = "description", length = 180)
    private String description;

    /**
     * Organization to which this role belongs.
     */
    @Expose

    @Column(name = "organization_id")
    private long organizationId;

    /**
     * Role's date of creation.
     */
    @Expose
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "created_date")
    private Calendar createdDate;

    /**
     * Last modified date.
     */
    @Expose
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "modified_date")
    private Calendar modifiedDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
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

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
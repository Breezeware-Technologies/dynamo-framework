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
import javax.xml.bind.annotation.XmlRootElement;

// import org.hibernate.envers.Audited;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

/**
 * Organization class that represents a top level organization or company or
 * firm. An organization will have users defined under it.
 */
@XmlRootElement
@Entity
// @Audited
@Table(name = "organization", schema = "dynamo")
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Expose
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    @SequenceGenerator(name = "seq_gen", sequenceName = "organization_seq", schema = "dynamo", allocationSize = 1)
    private long id;

    /**
     * Organization name.
     */
    @Expose
    @Column(name = "name", length = 45)
    @Pattern(regexp = "^(?=[A-Za-z]).*[\\s_A-Za-z\\d]{6,}$", message = "{Pattern.organization.name}")
    private String name;

    @Expose
    @Column(name = "description", length = 180)
    private String description;

    @Expose
    @Column(name = "created_date")
    private Calendar createdDate;

    @Expose
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
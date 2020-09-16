package net.breezeware.dynamo.auth.organization.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

/**
 * Entity to map a Dynamo App to an Dynamo organization.
 */
@XmlRootElement
@Entity
@Table(name = "organization_app_map", schema = "dynamo")
public class OrganizationAppMap implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unique key to identify a organization-app map,auto-generated value.
     */
    @Expose
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    @SequenceGenerator(name = "seq_gen", sequenceName = "organization_app_map_seq", schema = "dynamo",
            allocationSize = 1)
    private long id;

    /**
     * ID of organization being mapped.
     */
    @Expose
    @Column(name = "organization_id")
    private String organizationId;

    /**
     * ID of the app being mapped.
     */
    @Expose
    @Column(name = "app_id")
    private String appId;

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

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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
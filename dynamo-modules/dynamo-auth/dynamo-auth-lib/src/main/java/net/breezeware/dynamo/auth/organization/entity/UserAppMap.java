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
 * Entity to map a Dynamo App to an Dynamo user.
 */
@XmlRootElement
@Entity
@Table(name = "user_app_map", schema = "dynamo")
public class UserAppMap implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Unique key to identify a user-app map,auto-generated value.
     */
    @Expose
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    @SequenceGenerator(name = "seq_gen", sequenceName = "user_app_map_seq", schema = "dynamo", allocationSize = 1)
    private long id;

    /**
     * ID of user being mapped.
     */
    @Expose
    @Column(name = "user_id")
    private String userId;

    /**
     * ID of app being mapped.
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
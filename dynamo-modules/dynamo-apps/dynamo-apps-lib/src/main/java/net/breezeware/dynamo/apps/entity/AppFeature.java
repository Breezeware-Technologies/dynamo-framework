package net.breezeware.dynamo.apps.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

//import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

/**
 * Entity to represent a feature within Dynamo application.
 */
@XmlRootElement
@Entity
// @Audited
@Table(name = "app_feature", schema = "dynamo")
public class AppFeature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Expose
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    // value for parameter sequenceName requires the prefix 'dynamo.' to be added
    // even though we specify 'dynamo' in a separate parameter
    @SequenceGenerator(name = "seq_gen", sequenceName = "dynamo.app_feature_seq", schema = "dynamo", allocationSize = 1)
    private long id;

    @Expose
    @Column(name = "name", length = 45)
    @Size(min = 1, max = 45, message = "{Size.dynamoAppFeature.name}")
    private String name;

    @Expose
    @Column(name = "description")
    private String description;

    @Expose
    @Column(name = "app_id")
    private long appId;

    /**
     * Status of feature.
     */
    @Expose
    @Column(name = "status")
    private String status;

    @Expose
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "created_date")
    private Calendar createdDate;

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

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
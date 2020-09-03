package net.breezeware.dynamo.apps.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

/**
 * Entity to represent an App (application) in the context of Dynamo framework.
 */
@XmlRootElement
@Entity
@Table(name = "dynamo_app", schema = "dynamo")
public class DynamoApp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Expose
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    // value for parameter sequenceName requires the prefix 'dynamo.' to be added
    // even though we specify 'dynamo' in a separate parameter
    @SequenceGenerator(name = "seq_gen", sequenceName = "dynamo.dynamo_app_seq", schema = "dynamo", allocationSize = 1)
    private long id;

    @Expose
    @Size(min = 1, max = 45, message = "{Size.dynamoApp.name}")
    @Column(name = "name", length = 45, unique = true)
    private String name;

    @Expose
    @Size(min = 1, max = 180, message = "{Size.dynamoApp.description}")
    @Column(name = "description", length = 45, unique = true)
    private String description;

    /**
     * Status of app.
     */
    @Expose
    @Column(name = "status")
    private String status;

    @Expose
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "app_id")
    @Fetch(value = FetchMode.SUBSELECT)
    @Size(min = 1, message = "{Size.dynamoApp.appFeaturesMinSize}")
    private List<AppFeature> appFeatures;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AppFeature> getAppFeatures() {
        return appFeatures;
    }

    public void setAppFeatures(List<AppFeature> appFeatures) {
        this.appFeatures = appFeatures;
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
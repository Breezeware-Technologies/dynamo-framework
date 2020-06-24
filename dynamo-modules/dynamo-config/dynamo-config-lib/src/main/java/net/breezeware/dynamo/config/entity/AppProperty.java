package net.breezeware.dynamo.config.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

/**
 * Represents the 'Property' concept as defined by Spring Cloud Config project.
 * Spring cloud config server will expect a DB table called 'properties' with
 * the following fields of type string: application, profile, label, key, value.
 * 
 * https://github.com/spring-cloud/spring-cloud-config/blob/master/docs/src/main/asciidoc/spring-cloud-config.adoc#jdbc-backend
 * 
 */
@XmlRootElement
@Entity

// NOTE: Could not manage this properties table in a custom schema like 'dynamo'
// because Spring Cloud COnfig server
// was not able to find it. Therefore managing it in the public schema as it is.
// @Table(name = "properties", schema = "dynamo")

@Table(name = "properties")

public class AppProperty implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Expose
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    @SequenceGenerator(name = "seq_gen", sequenceName = "properties_seq", schema = "dynamo", allocationSize = 1)
    private long id;

    @Expose
    @Column(name = "application")
    private String application;

    @Expose
    @Column(name = "profile")
    private String profile;

    @Expose
    @Column(name = "label")
    private String label;

    @Expose
    @Column(name = "key")
    private String key;

    @Expose
    @Column(name = "value")
    @Size(min = 1, max = 45, message = "{Size.appConfigProperty.value}")
    private String value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
package net.breezeware.dynamo.auth.audit.entity;

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

import lombok.Getter;
import lombok.Setter;

/**
 * Entity to record the login details.
 * @author gowtham
 */
@XmlRootElement
@Entity
@Table(name = "audit_login", schema = "dynamo")
public class LoginAudit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Expose
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    @SequenceGenerator(name = "seq_gen", sequenceName = "audit_login_seq", schema = "dynamo", allocationSize = 1)
    @Getter
    @Setter
    private long id;

    @Expose
    @Column(name = "login_date")
    @Getter
    @Setter
    private Calendar loginDate;

    @Expose
    @Column(name = "ip_address")
    @Getter
    @Setter
    private String ipAddress;

    @Expose
    @Column(name = "client_details")
    @Getter
    @Setter
    private String clientDetails;

    @Expose
    @Column(name = "protocol")
    @Getter
    @Setter
    private String protocol;

    @Expose
    @Column(name = "login_email")
    @Getter
    @Setter
    private String loginEmail;

    @Expose
    @Column(name = "login_roles")
    @Getter
    @Setter
    private String loginRoles;

    @Expose
    @Column(name = "created_date")
    @Getter
    @Setter
    private Calendar createdDate;

    @Expose
    @Column(name = "modified_date")
    @Getter
    @Setter
    private Calendar modifiedDate;

    public String toString() {
        return new Gson().toJson(this);
    }
}
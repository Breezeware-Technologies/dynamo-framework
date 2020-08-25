package net.breezeware.dynamo.organization.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

// import org.hibernate.envers.Audited;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

/**
 * A Class for user-role mapping.
 * @author gowtham.
 */
@XmlRootElement
@Entity
// @Audited
@Table(name = "user_role_map", schema = "dynamo")
public class UserRoleMap implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unique key to identify a role,auto-generated value.
     */
    @Expose
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    @SequenceGenerator(name = "seq_gen", sequenceName = "user_role_map_seq", schema = "dynamo", allocationSize = 1)
    private long id;

    /**
     * Foreign key,refers to the id of user.
     */
    @Expose
    @Column(name = "user_id")
    private long userId;

    @Expose
    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public String toString() {
        return new Gson().toJson(this);
    }
}
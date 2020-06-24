package net.breezeware.dynamo.organization.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

//import org.hibernate.envers.Audited;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

/**
 * Application generated token used to generate a unique URL for setting the
 * password during User registration process.
 */
@XmlRootElement
@Entity
// @Audited
@Table(name = "user_registration_token", schema = "dynamo")
public class UserRegistrationToken implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Expose
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    @SequenceGenerator(name = "seq_gen", sequenceName = "user_registration_token_seq", schema = "dynamo", allocationSize = 1)
    private long id;

    @Expose
    @Column(name = "token", length = 45)
    private String token;

    @Expose
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User User;

    @Expose
    @Column(name = "token_created_date")
    private Calendar tokenCreatedDate;

    @Expose
    @Column(name = "token_expiry_date")
    private Calendar tokenExpiryDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User User) {
        this.User = User;
    }

    public Calendar getTokenCreatedDate() {
        return tokenCreatedDate;
    }

    public void setTokenCreatedDate(Calendar tokenCreatedDate) {
        this.tokenCreatedDate = tokenCreatedDate;
    }

    public Calendar getTokenExpiryDate() {
        return tokenExpiryDate;
    }

    public void setTokenExpiryDate(Calendar tokenExpiryDate) {
        this.tokenExpiryDate = tokenExpiryDate;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
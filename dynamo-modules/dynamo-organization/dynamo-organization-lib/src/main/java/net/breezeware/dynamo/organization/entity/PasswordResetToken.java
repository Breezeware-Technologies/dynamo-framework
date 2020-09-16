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

// import org.hibernate.envers.Audited;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

/**
 * Application generated token used to generate a unique URL for resetting the
 * password.
 */
@XmlRootElement
@Entity
// @Audited
@Table(name = "password_reset_token", schema = "dynamo")
public class PasswordResetToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @Expose
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    @SequenceGenerator(name = "seq_gen", sequenceName = "password_reset_token_seq", schema = "dynamo",
            allocationSize = 1)
    @Getter
    @Setter
    private long id;

    @Expose
    @Column(name = "token", length = 45)
    @Getter
    @Setter
    private String token;

    @Expose
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @Getter
    @Setter
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Expose
    @Column(name = "token_created_date")
    @Getter
    @Setter
    private Calendar tokenCreatedDate;

    @Expose
    @Column(name = "token_expiry_date")
    @Getter
    @Setter
    private Calendar tokenExpiryDate;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
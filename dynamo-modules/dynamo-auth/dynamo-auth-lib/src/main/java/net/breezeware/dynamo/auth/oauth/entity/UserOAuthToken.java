package net.breezeware.dynamo.auth.oauth.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import net.breezeware.dynamo.organization.entity.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Single OAuth token associated with a single Dynamo User. A Dynamo User entity
 * can have zero or more OAuth tokens associated with it.
 */
@Table(name = "auth_user_oauth_token", schema = "dynamo")
@ToString
@Entity
public class UserOAuthToken {

    public enum Application {
        ZOOM
    }

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_uotoken_seq_gen")
    @SequenceGenerator(name = "auth_uotoken_seq_gen", sequenceName = "auth_uotoken_seq", schema = "dynamo",
            allocationSize = 1)
    private long id;

    /**
     * ID assigned to a user.
     */
    @Getter
    @Setter
    @JoinColumn(name = "token_user", referencedColumnName = "id")
    @OneToOne
    private User tokenUser;

    /**
     * Application accessed using this access token. Example: Zoom, Fitbit, iHealth
     * etc...
     */
    @Getter
    @Setter
    @Column(name = "application")
    private String application;

    /**
     * Most recent access_token.
     */
    @Getter
    @Setter
    @Column(name = "access_token")
    private String accessToken;

    /**
     * Most recent refresh_token.
     */
    @Getter
    @Setter
    @Column(name = "refresh_token")
    private String refreshToken;

    /**
     * Expiration time of access_token.
     */
    @Getter
    @Setter
    @Column(name = "expires_at")
    private Instant expiresAt;

    /**
     * ID assigned to patient at source.
     */
    @Getter
    @Setter
    @Column(name = "user_id_at_source")
    private String userIdAtSource;

    /**
     * Instant when the token was created/updated in the DB.
     */
    @Getter
    @Setter
    @Column(name = "modified_date")
    private Instant modifiedDate;
}

package net.breezeware.dynamo.aws.iam.entity;

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

import lombok.Data;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.User;

/**
 * Class for OrganizationIamUserCredential to holds the Iam credential with
 * Organization.
 */
@Data
@Table(name = "organization_iam_user_credential", schema = "dynamo")
@Entity
public class OrganizationIamUserCredential {

    /**
     * Unique key to identify a organizationIamUserCredential,auto-generated value.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organization_iam_user_credential_seq_gen")
    @SequenceGenerator(name = "organization_iam_user_credential_seq_gen",
            sequenceName = "organization_iam_user_credential_seq", schema = "dynamo", allocationSize = 1)
    private long id;

    /**
     * IamUser ARN value.
     */
    @Column(name = "iam_arn")
    private String iamArn;

    /**
     * IamUser credential (accessKey) value.
     */
    @Column(name = "access_key")
    private String accessKey;

    /**
     * IamUser credential (secertKey) value.
     */
    @Column(name = "secert_key")
    private String secertKey;

    /**
     * Foreign key,refers to the id of user.
     */
    @OneToOne
    @JoinColumn(name = "user_admin_id", referencedColumnName = "id")
    private User user;

    /**
     * Defines the type of user organization.
     */
    @OneToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    /**
     * created date of the instance
     */
    @Column(name = "created_date")
    private Instant createdDate;

    /**
     * modified date of the instance
     */
    @Column(name = "modified_date")
    private Instant modifiedDate;

}

package net.breezeware.dynamo.aws.iam.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import net.breezeware.dynamo.organization.entity.Organization;

@Data
@Table(name = "organization_iam_user_credential", schema = "dynamo")
public class OrganizationIamUserCredential {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organization_iam_user_credential_seq_gen")
    @SequenceGenerator(name = "organization_iam_user_credential_seq_gen",
            sequenceName = "organization_iam_user_credential_seq", schema = "dynamo", allocationSize = 1)
    private long id;

    @Column(name = "iam_arn")
    private String iamArn;

    @Column(name = "access_key")
    private String accessKey;

    @Column(name = "secret_key")
    private String secertKey;

    @OneToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    @Column(name = "created_date")
    private Instant createdDate;

}

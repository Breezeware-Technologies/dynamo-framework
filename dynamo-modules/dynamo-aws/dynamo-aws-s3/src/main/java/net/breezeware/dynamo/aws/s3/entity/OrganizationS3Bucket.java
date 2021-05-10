package net.breezeware.dynamo.aws.s3.entity;

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

@Data
@Table(name = "organization_s3_bucket", schema = "dynamo")
@Entity
public class OrganizationS3Bucket {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organization_s3_bucket_seq_gen")
    @SequenceGenerator(name = "organization_s3_bucket_seq_gen", sequenceName = "organization_s3_bucket_seq",
            schema = "dynamo", allocationSize = 1)
    private long id;

    @Column(name = "bucket_name")
    private String bucketName;

    @OneToOne
    @JoinColumn(name = "created_user", referencedColumnName = "id")
    private User user;

    @OneToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "modified_date")
    private Instant modifiedDate;

}

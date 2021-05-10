package net.breezeware.dynamo.aws.s3.service.impl;

import java.time.Instant;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.identitymanagement.model.EntityAlreadyExistsException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.dynamo.aws.iam.entity.OrganizationIamUserCredential;
import net.breezeware.dynamo.aws.iam.service.api.AwsIdentityAccessManagementServiceApi;
import net.breezeware.dynamo.aws.s3.dao.OrganizationS3BucketRepository;
import net.breezeware.dynamo.aws.s3.entity.OrganizationS3Bucket;
import net.breezeware.dynamo.aws.s3.service.api.OrganizationS3BucketService;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.User;

@Service
@Slf4j
public class OrganizationS3BucketServiceImpl implements OrganizationS3BucketService {

    @Autowired
    AmazonS3 awsS3ClientBuilder;

    @Autowired
    OrganizationS3BucketRepository organizationS3BucketRepository;

    @Autowired
    AwsIdentityAccessManagementServiceApi awsIdentityAccessManagementService;

    public Bucket createBucket(CreateBucketRequest bucketRequest) {
        log.info("Entering createBucketForOrganization bucketRequest {}", bucketRequest);
        Bucket bucket = awsS3ClientBuilder.createBucket(bucketRequest);
        log.info("Leaving createBucketForOrganization,bucket {}", bucket);

        return bucket;
    }

    public OrganizationS3Bucket createBucketForOrganization(Organization organization, User user)
            throws EntityAlreadyExistsException {
        log.info("Entering createBucketForOrganization organization {},User {}", organization, user);

        Optional<OrganizationS3Bucket> optOrganizationS3Bucket = Optional
                .ofNullable(organizationS3BucketRepository.findByOrganization(organization));

        if (optOrganizationS3Bucket.isEmpty()) {

            OrganizationIamUserCredential organizationIamUserCredential = awsIdentityAccessManagementService
                    .CreateIamUserWithAwsServicePolicy(organization, user.getFirstName() + user.getLastName());

            CreateBucketRequest bucketRequest = new CreateBucketRequest(
                    organization.getName().replaceAll(" ", "_").toLowerCase());

            Bucket bucket = createBucket(bucketRequest);
            optOrganizationS3Bucket = Optional
                    .of(buildOrganizationS3Bucket(bucket, organizationIamUserCredential.getOrganization(), user));

        }

        log.info("Leaving createBucketForOrganization bucket optOrganizationS3Bucket{}");
        return null;

    }

    @Transactional
    public OrganizationS3Bucket buildOrganizationS3Bucket(Bucket bucket, Organization organization, User user) {
        OrganizationS3Bucket organizationS3Bucket = new OrganizationS3Bucket();

        organizationS3Bucket.setBucketName(bucket.getName());
        organizationS3Bucket.setOrganization(organization);
        organizationS3Bucket.setUser(user);
        organizationS3Bucket.setCreatedDate(Instant.now());
        organizationS3Bucket.setModifiedDate(Instant.now());

        organizationS3Bucket = saveOrganizationS3Bucket(organizationS3Bucket);
        return organizationS3Bucket;

    }

    private OrganizationS3Bucket saveOrganizationS3Bucket(OrganizationS3Bucket OrganizationS3Bucket) {
        log.info("Entering saveOrganizationIamUserCredential()");
        return organizationS3BucketRepository.save(OrganizationS3Bucket);
    }

}

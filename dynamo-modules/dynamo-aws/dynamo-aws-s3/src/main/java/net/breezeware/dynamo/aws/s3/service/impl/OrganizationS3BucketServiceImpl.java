package net.breezeware.dynamo.aws.s3.service.impl;

import java.time.Instant;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.dynamo.aws.iam.dao.OrganizationIamUserCredentialRepository;
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
    OrganizationS3BucketRepository organizationS3BucketRepository;

    @Autowired
    AwsIdentityAccessManagementServiceApi awsIdentityAccessManagementService;

    @Autowired
    OrganizationIamUserCredentialRepository organizationIamUserCredentialRepository;

    @Transactional
    public Bucket createBucket(CreateBucketRequest bucketRequest, AmazonS3 amazonS3) {
        log.info("Entering createBucketForOrganization bucketRequest {}", bucketRequest.getBucketName());
        Bucket bucket = amazonS3.createBucket(bucketRequest);
        log.info("Leaving createBucketForOrganization,bucket {}", bucket);

        return bucket;
    }

    // TODO: change repository iam to iam service.
    public Optional<OrganizationS3Bucket> createBucketForOrganization(Organization organization, User user) {
        log.info("Entering createBucketForOrganization organization {},User {}", organization, user);

        Optional<OrganizationS3Bucket> optOrganizationS3Bucket = retriveOrganizationS3Bucket(organization);

        if (optOrganizationS3Bucket.isEmpty()) {
            Optional<OrganizationIamUserCredential> optorganizationIamUserCredential = Optional
                    .ofNullable(organizationIamUserCredentialRepository.findByUser(user));
            if (optorganizationIamUserCredential.isEmpty()) {
                optorganizationIamUserCredential = awsIdentityAccessManagementService
                        .CreateIamUserWithAwsServicePolicy(organization, user);

            }
            try {
                Thread.sleep(10000);
                log.info("optorganizationIamUserCredential--->{}", optorganizationIamUserCredential.get());
                AmazonS3 amazonS3 = awsS3ClientBuilder(optorganizationIamUserCredential.get());
                log.info("amazonS3{}", amazonS3);

                CreateBucketRequest bucketRequest = new CreateBucketRequest(
                        organization.getName().replaceAll(" ", "-").toLowerCase());

                Bucket bucket = amazonS3.createBucket(bucketRequest);

                // Bucket bucket = createBucket(bucketRequest, amazonS3);
                optOrganizationS3Bucket = Optional.of(buildOrganizationS3Bucket(bucket, organization, user));
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        log.info("Leaving createBucketForOrganization bucket optOrganizationS3Bucket{}", optOrganizationS3Bucket);
        return optOrganizationS3Bucket;

    }

    public Optional<OrganizationS3Bucket> retriveOrganizationS3Bucket(Organization organization) {
        Optional<OrganizationS3Bucket> optOrganizationS3Bucket = Optional
                .ofNullable(organizationS3BucketRepository.findByOrganization(organization));
        return optOrganizationS3Bucket;
    }

    private AmazonS3 awsS3ClientBuilder(OrganizationIamUserCredential organizationIamUserCredential) {
        log.info("Entering awsS3ClientBuilder organizationIamUserCredential{}", organizationIamUserCredential);
        AWSCredentials credentials = new BasicAWSCredentials(organizationIamUserCredential.getAccessKey(),
                organizationIamUserCredential.getSecertKey());
        log.info("credentialsAccesskey {},credentialsSecretkey{}", credentials.getAWSAccessKeyId(),
                credentials.getAWSSecretKey());
        AmazonS3 amazonS3ClientBuilder = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_1.getName())
                .build();
        log.info("Leaving awsS3ClientBuilder amazonS3ClientBuilder{}", amazonS3ClientBuilder.toString());
        return amazonS3ClientBuilder;

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

    @Transactional
    public Optional<PutObjectResult> createDirectory(String bucketName, String folderName, Organization organization) {
        log.info("Entering createDirectory bucketName{} ,folderName{}", bucketName, folderName);
        Optional<OrganizationIamUserCredential> optOrganizationIamUserCredential = awsIdentityAccessManagementService
                .retriveOrganizationIamUserCredential(organization);
        Optional<PutObjectResult> putObjectResult = Optional.empty();
        if (optOrganizationIamUserCredential.isPresent()) {
            AmazonS3 amazonS3 = awsS3ClientBuilder(optOrganizationIamUserCredential.get());
            putObjectResult = Optional.of(amazonS3.putObject(bucketName, folderName + "/", ""));
        }

        log.info("Leaving createDirectory ");

        return putObjectResult;
    }

}

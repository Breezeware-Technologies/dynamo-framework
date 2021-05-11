package net.breezeware.dynamo.aws.s3.service.api;

import java.util.Optional;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import net.breezeware.dynamo.aws.s3.entity.OrganizationS3Bucket;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.User;

public interface OrganizationS3BucketService {

    Bucket createBucket(CreateBucketRequest bucketRequest, AmazonS3 amazonS3);

    Optional<OrganizationS3Bucket> createBucketForOrganization(Organization organization, User user);

    OrganizationS3Bucket buildOrganizationS3Bucket(Bucket bucket, Organization organization, User user);

    Optional<PutObjectResult> createDirectory(String bucketName, String folderName, Organization organization);

    Optional<OrganizationS3Bucket> retriveOrganizationS3Bucket(Organization organization);

}

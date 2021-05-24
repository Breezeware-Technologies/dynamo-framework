package net.breezeware.dynamo.aws.s3.service.api;

import java.util.Optional;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectResult;

import net.breezeware.dynamo.aws.iam.entity.OrganizationIamUserCredential;
import net.breezeware.dynamo.aws.s3.entity.OrganizationS3Bucket;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.User;

public interface AwsS3BucketService {
    /**
     * create AWS s3 bucket for an organization
     * @param organization AWS s3 bucket created for this organization.
     * @param user         iam user will be created for this user.
     * @return OrganizationS3Bucket.
     */
    Optional<OrganizationS3Bucket> createBucketForOrganization(Organization organization, User user);

    /**
     * create directory for the user in the user's organization S3 bucket.
     * @param bucketName   name of the bucket for the specified organization.
     * @param folderName   name of the directory for the user.
     * @param organization organizationId Key assigned in the
     *                     OrganizationIamUserCredential
     * @return PutObjectResult user's directory metadata.
     */
    Optional<PutObjectResult> createDirectory(String bucketName, String folderName, Organization organization);

    /**
     * retrive OrganizationS3Bucket using organization.
     * @param organization organizationId Key assigned in the OrganizationS3Bucket.
     * @return OrganizationS3Bucket.
     */
    Optional<OrganizationS3Bucket> retriveOrganizationS3Bucket(Organization organization);

    /**
     * delete object in the aws s3 bucket.
     * @param organizationIamUserCredential OrganizationIamUserCredential for
     *                                      specific aws s3 bucket.
     * @param bucketName                    Name of the bucket where the object is
     *                                      associated.
     * @param keyName                       value for an Object in S3
     *                                      bucket.(i.e:foldername/filename).
     * @throws AmazonS3Exception Exception for bucket or object does not exists.
     */
    void deleteobjectInBucket(OrganizationIamUserCredential organizationIamUserCredential, String bucketName,
            String keyName) throws AmazonS3Exception;

}

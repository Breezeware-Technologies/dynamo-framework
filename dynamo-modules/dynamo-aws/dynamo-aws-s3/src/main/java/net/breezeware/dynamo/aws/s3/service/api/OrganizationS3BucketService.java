package net.breezeware.dynamo.aws.s3.service.api;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;

public interface OrganizationS3BucketService {

    Bucket createBucket(CreateBucketRequest bucketRequest);

}

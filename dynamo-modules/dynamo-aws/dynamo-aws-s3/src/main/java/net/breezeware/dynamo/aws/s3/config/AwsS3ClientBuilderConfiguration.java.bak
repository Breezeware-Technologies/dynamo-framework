package net.breezeware.dynamo.aws.s3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import net.breezeware.dynamo.aws.iam.entity.OrganizationIamUserCredential;

@Configuration
public class AwsS3ClientBuilderConfiguration {

    @Bean(name = "awsS3ClientBuilder")
    public AmazonS3 awsS3ClientBuilder(OrganizationIamUserCredential organizationIamUserCredential) {

        AmazonS3 amazonS3ClientBuilder = AmazonS3ClientBuilder.standard()
                .withCredentials((AWSCredentialsProvider) new BasicAWSCredentials(
                        organizationIamUserCredential.getAccessKey(), organizationIamUserCredential.getSecertKey()))
                .withRegion(Regions.US_EAST_1.name()).build();
        return amazonS3ClientBuilder;

    }

}

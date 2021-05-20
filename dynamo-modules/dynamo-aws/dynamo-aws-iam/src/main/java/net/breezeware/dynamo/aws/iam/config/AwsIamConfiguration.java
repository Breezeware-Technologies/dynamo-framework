package net.breezeware.dynamo.aws.iam.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;

@Configuration
public class AwsIamConfiguration {
    @Value("${aws.iam.accesskey}")
    String accessKey;

    @Value("${aws.iam.secretkey}")
    String secretKey;

    @Bean(name = "awsIamuserConfiguration")
    public AmazonIdentityManagement awsIamuserConfiguration() {

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        AmazonIdentityManagement iamClient = AmazonIdentityManagementClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_1.getName())
                .build();
        return iamClient;

    }

}

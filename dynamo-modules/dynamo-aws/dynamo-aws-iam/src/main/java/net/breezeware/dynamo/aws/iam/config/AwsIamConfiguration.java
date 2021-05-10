package net.breezeware.dynamo.aws.iam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;

@Configuration
public class AwsIamConfiguration {

    @Bean(name= "awsIamuserConfiguration")
    public AmazonIdentityManagement awsIamuserConfiguration() {
        AmazonIdentityManagement iamClient = AmazonIdentityManagementClientBuilder.standard()
                .withRegion(Regions.US_EAST_1.getName()).build();
        return iamClient;

    }

}

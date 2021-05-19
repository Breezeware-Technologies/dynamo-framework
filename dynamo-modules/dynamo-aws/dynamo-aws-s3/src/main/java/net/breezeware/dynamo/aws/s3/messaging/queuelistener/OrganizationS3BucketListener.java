package net.breezeware.dynamo.aws.s3.messaging.queuelistener;

import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.dynamo.aws.s3.entity.OrganizationS3Bucket;
import net.breezeware.dynamo.aws.s3.service.api.AwsS3BucketService;
import net.breezeware.dynamo.organization.messaging.entity.UserCreatedMessage;

@Slf4j
@Component
public class OrganizationS3BucketListener {

    @Autowired
    AwsS3BucketService awsS3BucketService;

    @RabbitListener(queues = "${rabbitmq.dynamo.organizationS3Bucket.queue}")
    public void createS3Bucket(UserCreatedMessage userCreatedMessage) {
        log.info("Entering createS3Bucket() userCreatedMessage {}", userCreatedMessage);

        Optional<OrganizationS3Bucket> optOrganizationS3Bucket = awsS3BucketService
                .createBucketForOrganization(userCreatedMessage.getOrganization(), userCreatedMessage.getUser());
        if (optOrganizationS3Bucket.isPresent()) {
            log.info("S3Bucket created optOrganizationS3Bucket {}", optOrganizationS3Bucket.get());
        }
        log.info("Leaving createS3Bucket()");
    }

}

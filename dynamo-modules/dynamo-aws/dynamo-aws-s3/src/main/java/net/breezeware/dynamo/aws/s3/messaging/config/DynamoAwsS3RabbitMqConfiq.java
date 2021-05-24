package net.breezeware.dynamo.aws.s3.messaging.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoAwsS3RabbitMqConfiq {

    @Autowired
    TopicExchange dynamoOrgTopicExchange;

    @Value("${rabbitmq.dynamo.organizationS3Bucket.queue}")
    String dynamoOrganizationS3BucketQueue;

    @Bean
    Queue dynamoOrganizationS3BucketQueue() {
        return new Queue(dynamoOrganizationS3BucketQueue, true);
    }

    @Bean
    Binding dynamouserCreatedS3Binding(Queue dynamoOrganizationS3BucketQueue, TopicExchange dynamoOrgTopicExchange) {
        return BindingBuilder.bind(dynamoOrganizationS3BucketQueue).to(dynamoOrgTopicExchange).with("key.dyn_org.user.created");
    }
}

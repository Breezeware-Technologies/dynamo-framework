package net.breezeware.dynamo.organization.messaging;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ Configuration for Dynamo Organization Lib
 * @author guru
 */
@Configuration
public class RabbitMqConfiguration {

    @Value("${rabbitmq.dynamo.org.user.exchange}")
    String dynamoUserExchange;

    @Bean
    public TopicExchange dynamoUserTopicExchange() {
        return new TopicExchange(dynamoUserExchange);
    }
}

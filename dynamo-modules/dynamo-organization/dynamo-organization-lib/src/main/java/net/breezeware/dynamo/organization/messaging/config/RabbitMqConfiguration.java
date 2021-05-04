package net.breezeware.dynamo.organization.messaging.config;

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

    @Value("${rabbitmq.dynamo.org.exchange}")
    String dynamoOrgExchange;

    @Bean(name = "dynamoOrgTopicExchange")
    public TopicExchange dynamoOrgTopicExchange() {
        return new TopicExchange(dynamoOrgExchange);
    }
}

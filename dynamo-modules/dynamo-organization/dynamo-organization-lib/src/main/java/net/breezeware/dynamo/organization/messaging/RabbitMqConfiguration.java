package net.breezeware.dynamo.organization.messaging;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ Configuration for Dynamo Organization Lib
 * @author guru
 *
 */
@Configuration
public class RabbitMqConfiguration {

//    @Value("${rabbitmq.dynamoOrgExchange}")
//    String dynamoOrgExchange;

    @Bean
    public TopicExchange dynamoOrgExchange() {
        return new TopicExchange("exT.dyn_org");
    }
}

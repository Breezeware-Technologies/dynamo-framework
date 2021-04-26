package net.breezeware.dynamo.organization.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserCreationEventListener {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.dynamo.org.user.exchange}")
    String dynamoUserExchange;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void dynamoUserCreatedListener(UserCreatedMessage userCreatedMessage) {

        // sending message to RabbitMQ
        log.info("Sending message to RabbitMQ from Dynamo Organization module...");
        rabbitTemplate.convertAndSend(dynamoUserExchange, "key.dyn_org.user.created", userCreatedMessage);
        log.info("Sent message to RabbitMQ from Dynamo Organization module... UserCreatedMessage {}",
                userCreatedMessage);

    }

}
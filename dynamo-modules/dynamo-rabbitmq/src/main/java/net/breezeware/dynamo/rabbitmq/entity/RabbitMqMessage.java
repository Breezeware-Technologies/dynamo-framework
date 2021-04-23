package net.breezeware.dynamo.rabbitmq.entity;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

/**
 * Base class to record meta-data about RabbitMq message.
 */
@Data
public abstract class RabbitMqMessage {

    /**
     * Dynamo generated message id.
     */
    private UUID messageId;

    /**
     * Instant at which the message is created.
     */
    private Instant createdDate;
}

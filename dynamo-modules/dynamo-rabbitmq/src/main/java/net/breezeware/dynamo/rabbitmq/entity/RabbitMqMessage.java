package net.breezeware.dynamo.rabbitmq.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import lombok.Data;

/**
 * Base class to record meta-data about RabbitMq message.
 */
@Data
public abstract class RabbitMqMessage implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Dynamo generated message id.
     */
    private UUID messageId;

    /**
     * Instant at which the message is created.
     */
    private Instant createdDate;
}

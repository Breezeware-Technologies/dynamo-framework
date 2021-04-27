package net.breezeware.dynamo.organization.messaging;

import java.io.Serializable;

import lombok.Data;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.User;
import net.breezeware.dynamo.rabbitmq.entity.RabbitMqMessage;

@Data
public class UserCreatedMessage extends RabbitMqMessage implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Organization organization;

    private User user;

}
package net.breezeware.dynamo.organization.messaging;

import java.io.Serializable;

import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.User;

import lombok.Data;

@Data
public class UserCreatedMessage extends RabbitMqMessage implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Organization organization;

    private User user;

}
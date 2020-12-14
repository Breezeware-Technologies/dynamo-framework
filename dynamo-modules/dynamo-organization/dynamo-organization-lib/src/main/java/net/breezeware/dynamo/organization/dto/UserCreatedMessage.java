package net.breezeware.dynamo.organization.dto;

import java.io.Serializable;

import lombok.Data;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.User;
import net.breezeware.dynamo.organization.messaging.RabbitMqMessage;

@Data
public class UserCreatedMessage extends RabbitMqMessage implements Serializable {

    private Organization organization;

    private User user;

}
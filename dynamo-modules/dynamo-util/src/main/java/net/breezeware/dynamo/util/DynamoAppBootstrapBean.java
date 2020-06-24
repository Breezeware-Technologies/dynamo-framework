package net.breezeware.dynamo.util;

import java.util.List;

public interface DynamoAppBootstrapBean {

    String getCurrentUserEmail();

    long getCurrentUserOrganizationId();

    List<String> getCurrentUserRoles();
}
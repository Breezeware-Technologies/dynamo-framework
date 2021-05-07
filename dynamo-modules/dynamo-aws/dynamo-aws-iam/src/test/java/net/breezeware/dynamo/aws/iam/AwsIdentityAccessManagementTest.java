package net.breezeware.dynamo.aws.iam;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.dynamo.aws.iam.entity.OrganizationIamUserCredential;
import net.breezeware.dynamo.aws.iam.service.api.AwsIdentityAccessManagementServiceApi;
import net.breezeware.dynamo.aws.iam.service.impl.AwsIdentityAccessManagementServiceImpl;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.service.api.OrganizationService;

@Slf4j
@ContextConfiguration(classes = TestApplication.class)
public class AwsIdentityAccessManagementTest extends AbstractTestNGSpringContextTests {

    @Autowired
    OrganizationService organizationService;

    AwsIdentityAccessManagementServiceApi accessManagementServiceApi = new AwsIdentityAccessManagementServiceImpl();

    @Test
    public void createIamUserwithPolicyTest() {
        log.info("Entering createIamUserwithPolicyTest() ");
        Organization organization = new Organization();
        OrganizationIamUserCredential credential = accessManagementServiceApi
                .CreateIamUserWithAwsServicePolicy(organization, "admin");
        log.info("Leaving createIamUserwithPolicyTest() {}", credential);

    }
}

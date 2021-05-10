package net.breezeware.dynamo.aws.iam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.dynamo.aws.iam.entity.OrganizationIamUserCredential;
import net.breezeware.dynamo.aws.iam.service.api.AwsIdentityAccessManagementServiceApi;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.service.api.OrganizationService;

@RestController("/create")
@Slf4j
public class AwsIdentityAccessManagementRestController {

    @Autowired
    AwsIdentityAccessManagementServiceApi accessManagementServiceApi;

    @Autowired
    OrganizationService organizationService;

    @PostMapping(value = "/iamUser")
    public OrganizationIamUserCredential createIamUserwithPolicyController() {
        log.info("Entering createIamUserwithPolicyController ");
        Organization organization = organizationService.findOrganizationByName("Breeze Tech");

        OrganizationIamUserCredential credential = accessManagementServiceApi
                .CreateIamUserWithAwsServicePolicy(organization, "admin");
        log.info("Leaving createIamUserwithPolicyController {}", credential);
        return credential;

    }

}

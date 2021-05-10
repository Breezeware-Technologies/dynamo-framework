package net.breezeware.dynamo.aws.iam.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.identitymanagement.model.EntityAlreadyExistsException;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.dynamo.aws.iam.entity.OrganizationIamUserCredential;
import net.breezeware.dynamo.aws.iam.service.api.AwsIdentityAccessManagementServiceApi;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.service.api.OrganizationService;

@RestController
@Slf4j
@RequestMapping("/create")
public class AwsIdentityAccessManagementRestController {

	@Autowired
	AwsIdentityAccessManagementServiceApi accessManagementServiceApi;

	@Autowired
	OrganizationService organizationService;

	@GetMapping(value = "/iamUser")
	public OrganizationIamUserCredential createIamUserwithPolicyController() {
		log.info("Entering createIamUserwithPolicyController ");
		OrganizationIamUserCredential credential = new OrganizationIamUserCredential();
		Organization organization = organizationService.findOrganizationByName("Breeze Tech");
		try {
		 credential = accessManagementServiceApi
					.CreateIamUserWithAwsServicePolicy(organization, "Siva1");
		} catch (EntityAlreadyExistsException e) {
			return new OrganizationIamUserCredential();
		}

		log.info("Leaving createIamUserwithPolicyController {}");
		return credential;

	}

}
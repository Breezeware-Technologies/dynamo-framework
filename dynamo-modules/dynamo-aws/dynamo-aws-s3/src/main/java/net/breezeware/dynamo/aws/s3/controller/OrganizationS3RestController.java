package net.breezeware.dynamo.aws.s3.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.breezeware.dynamo.aws.s3.entity.OrganizationS3Bucket;
import net.breezeware.dynamo.aws.s3.service.api.OrganizationS3BucketService;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.User;
import net.breezeware.dynamo.organization.service.api.OrganizationService;

@RestController
@RequestMapping(value = "/s3")
public class OrganizationS3RestController {
	@Autowired
	OrganizationS3BucketService organizationS3BucketService;
	
	@Autowired
	OrganizationService organizationService;
	
	@GetMapping(value = "/createBucket")
	public OrganizationS3Bucket createBucket() {
		Organization organization = organizationService.findOrganizationById(2);
		User user = organizationService.getUserById(1);
		
		Optional<OrganizationS3Bucket> optOrganizationS3Bucket = organizationS3BucketService.createBucketForOrganization(organization, user);
		return optOrganizationS3Bucket.get();
	}

}

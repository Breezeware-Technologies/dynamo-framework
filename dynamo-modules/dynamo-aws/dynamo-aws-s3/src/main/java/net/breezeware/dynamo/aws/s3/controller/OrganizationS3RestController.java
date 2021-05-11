package net.breezeware.dynamo.aws.s3.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.breezeware.dynamo.aws.s3.entity.OrganizationS3Bucket;
import net.breezeware.dynamo.aws.s3.service.api.OrganizationS3BucketService;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.Role;
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
    public List<OrganizationS3Bucket> createBucket() {

        Optional<OrganizationS3Bucket> optOrganizationS3Bucket = Optional.empty();
        List<OrganizationS3Bucket> organizationS3BucketList = new ArrayList<OrganizationS3Bucket>();
        List<Organization> organizationList = organizationService.findAllOrganizations();
        List<User> userList = new ArrayList<User>();
        for (Organization organization2 : organizationList) {
            Optional<Role> currentRole = organizationService.findRoleByName(organization2.getId(), "ROLE_SYSTEM_ADMIN");
            if (currentRole.isPresent()) {
                userList = organizationService.findUsers(organization2.getId(), currentRole.get().getName());
                optOrganizationS3Bucket = organizationS3BucketService.createBucketForOrganization(organization2,
                        userList.get(0));
            } else {
                // TODO : Any other Possible role user Role for creaion IAM USER of a
                // organization.
                break;
            }
            organizationS3BucketList.add(optOrganizationS3Bucket.get());
        }

        return organizationS3BucketList;
    }

}

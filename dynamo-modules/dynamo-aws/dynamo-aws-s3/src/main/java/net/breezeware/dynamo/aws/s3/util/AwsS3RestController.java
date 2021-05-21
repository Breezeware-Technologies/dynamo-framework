package net.breezeware.dynamo.aws.s3.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.dynamo.aws.s3.entity.OrganizationS3Bucket;
import net.breezeware.dynamo.aws.s3.service.api.AwsS3BucketService;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.Role;
import net.breezeware.dynamo.organization.entity.User;
import net.breezeware.dynamo.organization.service.api.OrganizationService;

/**
 * create AWS IAM user and AWS s3 bucket for the specific organization.
 */
@RestController
@RequestMapping(value = "/s3")
@Slf4j
public class AwsS3RestController {
    @Autowired
    AwsS3BucketService organizationS3BucketService;

    @Autowired
    OrganizationService organizationService;

    @PostMapping(value = "/createBucket")
    public List<OrganizationS3Bucket> createBucket() {
        log.info("Entering createBucket()");
        List<Organization> organizationList = organizationService.findAllOrganizations();

        List<OrganizationS3Bucket> organizationS3BucketList = new ArrayList<OrganizationS3Bucket>();

        Optional<OrganizationS3Bucket> optOrganizationS3Bucket = Optional.empty();

        for (Organization organization : organizationList) {
            log.info("organization{}", organization);
            Optional<Role> currentRole = organizationService.findRoleByName(organization.getId(), "ORGANIZATION_ADMIN");
            log.info("currentRole1{}", currentRole);
            if (currentRole.isPresent()) {
                log.info("currentRole2{}", currentRole);
                List<User> userList = organizationService.findUsers(organization.getId(), currentRole.get().getName());
                log.info("userList{}", userList);
                optOrganizationS3Bucket = organizationS3BucketService.createBucketForOrganization(organization,
                        userList.get(0));
            } else {
                continue;
            }
            if (optOrganizationS3Bucket.isPresent()) {
                organizationS3BucketList.add(optOrganizationS3Bucket.get());
            }
            log.info("Leaving createBucket1 ----> organizationS3BucketList{}()", organizationS3BucketList);
        }
        log.info("Leaving createBucket2 ----> organizationS3BucketList{}()", organizationS3BucketList);

        return organizationS3BucketList;
    }

}

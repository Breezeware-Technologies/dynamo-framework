package net.breezeware.dynamo.aws.s3.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.dynamo.aws.s3.entity.OrganizationS3Bucket;
import net.breezeware.dynamo.organization.entity.Organization;

@Repository
public interface OrganizationS3BucketRepository extends JpaRepository<OrganizationS3Bucket, Long> {

    Optional<OrganizationS3Bucket> findByOrganization(Organization organization);

}

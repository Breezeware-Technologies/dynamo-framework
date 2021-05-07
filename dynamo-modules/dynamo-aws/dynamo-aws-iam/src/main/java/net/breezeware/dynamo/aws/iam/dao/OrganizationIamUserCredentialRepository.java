package net.breezeware.dynamo.aws.iam.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.dynamo.aws.iam.entity.OrganizationIamUserCredential;

@Repository
public interface OrganizationIamUserCredentialRepository extends JpaRepository<OrganizationIamUserCredential, Long> {

}

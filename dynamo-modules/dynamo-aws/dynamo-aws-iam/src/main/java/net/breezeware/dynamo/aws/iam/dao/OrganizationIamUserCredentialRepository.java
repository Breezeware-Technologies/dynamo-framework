package net.breezeware.dynamo.aws.iam.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.dynamo.aws.iam.entity.OrganizationIamUserCredential;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.User;

@Repository
public interface OrganizationIamUserCredentialRepository extends JpaRepository<OrganizationIamUserCredential, Long> {

    Optional<OrganizationIamUserCredential> findByOrganization(Organization organization);
    
    Optional<OrganizationIamUserCredential> findByUser(User user);

}

package net.breezeware.dynamo.aws.iam.service.api;

import java.util.Optional;

import net.breezeware.dynamo.aws.iam.entity.OrganizationIamUserCredential;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.User;

/**
 * Interface to manage aspects like AWS IAM credential.
 */
public interface AwsIdentityAccessManagementService {
    /**
     * create Iam user and provide policy along with security credential for an
     * organization.
     * @param organization Iam user created for this organization.
     * @param user         Iam user created from this user name that holds the role
     *                     of organization admin.
     * @return organizationIamUserCredential.
     */
    Optional<OrganizationIamUserCredential> createIamUserWithAwsServicePolicy(Organization organization, User user);

    /**
     * retrive organizationIamUserCredential using organization.
     * @param organization organizationId Key assigned in the
     *                     OrganizationIamUserCredential.
     * @return organizationIamUserCredential
     */
    Optional<OrganizationIamUserCredential> retriveOrganizationIamUserCredential(Organization organization);

    /**
     * retrive organizationIamUserCredential using user.
     * @param user userId Key assigned in the OrganizationIamUserCredential.
     * @return organizationIamUserCredential.
     */
    Optional<OrganizationIamUserCredential> retriveOrganizationIamUserCredential(User user);

}

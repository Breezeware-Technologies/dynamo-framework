package net.breezeware.dynamo.aws.iam.service.api;

import java.util.Optional;

import net.breezeware.dynamo.aws.iam.entity.OrganizationIamUserCredential;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.User;

public interface AwsIdentityAccessManagementService {

//    CreateUserResult createIamUser(CreateUserRequest createUserRequest);

//    CreateAccessKeyResult createIamUserAccessKey(CreateUserResult createUserResult);

//    AttachUserPolicyResult attachPolicyForIamUser(CreateUserResult createUserResult);

	Optional<OrganizationIamUserCredential> createIamUserWithAwsServicePolicy(Organization organization, User user);

	Optional<OrganizationIamUserCredential> retriveOrganizationIamUserCredential(Organization organization);

	Optional<OrganizationIamUserCredential> retriveOrganizationIamUserCredential(User user);

}

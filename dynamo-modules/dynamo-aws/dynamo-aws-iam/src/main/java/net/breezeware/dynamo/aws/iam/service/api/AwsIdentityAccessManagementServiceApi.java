package net.breezeware.dynamo.aws.iam.service.api;

import java.util.Optional;

import com.amazonaws.services.identitymanagement.model.AttachUserPolicyResult;
import com.amazonaws.services.identitymanagement.model.CreateAccessKeyResult;
import com.amazonaws.services.identitymanagement.model.CreateUserRequest;
import com.amazonaws.services.identitymanagement.model.CreateUserResult;
import com.amazonaws.services.identitymanagement.model.EntityAlreadyExistsException;

import net.breezeware.dynamo.aws.iam.entity.OrganizationIamUserCredential;
import net.breezeware.dynamo.organization.entity.Organization;

public interface AwsIdentityAccessManagementServiceApi {

    CreateUserResult createIamUser(CreateUserRequest createUserRequest) throws EntityAlreadyExistsException;

    CreateAccessKeyResult createIamUserAccessKey(CreateUserResult createUserResult);

    AttachUserPolicyResult attachPolicyForIamUser(CreateUserResult createUserResult);

    OrganizationIamUserCredential CreateIamUserWithAwsServicePolicy(Organization organization,
            String organizationAdminName) throws EntityAlreadyExistsException;

    Optional<OrganizationIamUserCredential> retriveOrganizationIamUserCredential(Organization organization);

}

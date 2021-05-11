package net.breezeware.dynamo.aws.iam.service.api;

import java.util.Optional;

import com.amazonaws.services.identitymanagement.model.AttachUserPolicyResult;
import com.amazonaws.services.identitymanagement.model.CreateAccessKeyResult;
import com.amazonaws.services.identitymanagement.model.CreateUserRequest;
import com.amazonaws.services.identitymanagement.model.CreateUserResult;
import com.amazonaws.services.identitymanagement.model.GetUserResult;

import net.breezeware.dynamo.aws.iam.entity.OrganizationIamUserCredential;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.User;

public interface AwsIdentityAccessManagementServiceApi {

    CreateUserResult createIamUser(CreateUserRequest createUserRequest) ;

    CreateAccessKeyResult createIamUserAccessKey(CreateUserResult createUserResult);

    AttachUserPolicyResult attachPolicyForIamUser(CreateUserResult createUserResult);

    Optional<OrganizationIamUserCredential> CreateIamUserWithAwsServicePolicy(Organization organization,
            User user) ;

    Optional<OrganizationIamUserCredential> retriveOrganizationIamUserCredential(Organization organization);
    
    GetUserResult retrieveUserCredential(String userName);

}

package net.breezeware.dynamo.aws.iam.service.impl;

import java.time.Instant;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.AttachUserPolicyRequest;
import com.amazonaws.services.identitymanagement.model.AttachUserPolicyResult;
import com.amazonaws.services.identitymanagement.model.CreateAccessKeyRequest;
import com.amazonaws.services.identitymanagement.model.CreateAccessKeyResult;
import com.amazonaws.services.identitymanagement.model.CreateUserRequest;
import com.amazonaws.services.identitymanagement.model.CreateUserResult;
import com.amazonaws.services.identitymanagement.model.ListUsersResult;
import com.amazonaws.services.identitymanagement.model.User;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.dynamo.aws.iam.dao.OrganizationIamUserCredentialRepository;
import net.breezeware.dynamo.aws.iam.entity.OrganizationIamUserCredential;
import net.breezeware.dynamo.aws.iam.service.api.AwsIdentityAccessManagementServiceApi;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.service.api.OrganizationService;

@Slf4j
@Service
public class AwsIdentityAccessManagementServiceImpl implements AwsIdentityAccessManagementServiceApi {

    @Autowired
    OrganizationIamUserCredentialRepository organizationIamUserCredentialRepository;

    @Autowired
    OrganizationService organizationService;

     @Autowired
     AmazonIdentityManagement awsIamuserConfiguration;

//    final AmazonIdentityManagement iam = AmazonIdentityManagementClientBuilder.standard()
//            .withRegion(Regions.US_EAST_1.getName()).build();

    @Transactional
    public Optional<CreateUserResult> createIamUser(CreateUserRequest createUserRequest) {
        log.info("Entering createIamUser() {}", createUserRequest.getUserName());

        ListUsersResult userList = awsIamuserConfiguration.listUsers();
        Optional<CreateUserResult> userResult = Optional.empty();
        for (User user : userList.getUsers()) {
        	log.info("User --->{},get ----> {}",user.getUserName(),createUserRequest.getUserName());
            if (!user.getUserName().equals(createUserRequest.getUserName())) {
            	log.info("User --->{} get --> {}",user.getUserName(),createUserRequest.getUserName());
                userResult = Optional.of(awsIamuserConfiguration.createUser(createUserRequest));
            }else {
                break;
            }
        }

        log.info("Leaving createIamUser() {}", userResult);

        return userResult;
    }

    @Transactional
    public CreateAccessKeyResult createIamUserAccessKey(CreateUserResult createUserResult) {
        log.info("Entering createIamUserAccessKey() {}", createUserResult);
        CreateAccessKeyRequest createAccessKeyRequest = new CreateAccessKeyRequest();
        createAccessKeyRequest.setUserName(createUserResult.getUser().getUserName());
        CreateAccessKeyResult createAccessKeyResult = awsIamuserConfiguration.createAccessKey(createAccessKeyRequest);
        log.info("Leaving createIamUserAccessKey() {}", createAccessKeyResult);
        return createAccessKeyResult;
    }

    @Transactional
    public AttachUserPolicyResult attachPolicyForIamUser(CreateUserResult createUserResult) {
        log.info("Entering attachPolicyForIamUser() {}", createUserResult);
        AttachUserPolicyRequest attachUserPolicyRequest = new AttachUserPolicyRequest();
        attachUserPolicyRequest.setPolicyArn("arn:aws:iam::aws:policy/AmazonS3FullAccess");
        attachUserPolicyRequest.setUserName(createUserResult.getUser().getUserName());
        AttachUserPolicyResult attachUserPolicyResult = awsIamuserConfiguration.attachUserPolicy(attachUserPolicyRequest);
        log.info("Leaving attachPolicyForIamUser() {}", attachUserPolicyResult);

        return attachUserPolicyResult;

    }

    @Transactional
    public Optional<OrganizationIamUserCredential> CreateIamUserWithAwsServicePolicy(Organization organization,
            String organizationAdminName) {
        log.info("Entering CreateIamUserWithAwsServicePolicy() Organization{} ,organizationAdminName{} ", organization,
                organizationAdminName);
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUserName(organizationAdminName);
        Optional<OrganizationIamUserCredential> organizationIamUserCredential = Optional.empty();
        Optional<CreateUserResult> createUserResult = createIamUser(createUserRequest);
        if(createUserResult.isPresent()) {
        
        attachPolicyForIamUser(createUserResult.get());
        CreateAccessKeyResult createAccessKeyResult = createIamUserAccessKey(createUserResult.get());

         organizationIamUserCredential = Optional.of(buildAndSaveOrganizationIamUserCredential(
                organization, createUserResult.get(), createAccessKeyResult));
        log.info("Leaving CreateIamUserWithAwsServicePolicy() {}", organizationIamUserCredential);
        }
        return organizationIamUserCredential;
    }

    private OrganizationIamUserCredential buildAndSaveOrganizationIamUserCredential(Organization organization,
            CreateUserResult createUserResult, CreateAccessKeyResult createAccessKeyResult) {
        log.info(
                "Entering buildAndSaveOrganizationIamUserCredential() Organization{}, CreateUserResult {},CreateAccessKeyResult{}",
                organization, createUserResult, createAccessKeyResult);
        OrganizationIamUserCredential organizationIamUserCredential = new OrganizationIamUserCredential();
        organizationIamUserCredential.setAccessKey(createAccessKeyResult.getAccessKey().getAccessKeyId());
        organizationIamUserCredential.setSecertKey(createAccessKeyResult.getAccessKey().getSecretAccessKey());
        organizationIamUserCredential.setOrganization(organization);
        organizationIamUserCredential.setIamArn(createUserResult.getUser().getArn());
        organizationIamUserCredential.setCreatedDate(Instant.now());

        organizationIamUserCredential = saveOrganizationIamUserCredential(organizationIamUserCredential);
        log.info("Leaving buildAndSaveOrganizationIamUserCredential() {}", organizationIamUserCredential);
        return organizationIamUserCredential;
    }

    private OrganizationIamUserCredential saveOrganizationIamUserCredential(
            OrganizationIamUserCredential organizationIamUserCredential) {
        log.info("Entering saveOrganizationIamUserCredential()");
        return organizationIamUserCredentialRepository.save(organizationIamUserCredential);
    }

}

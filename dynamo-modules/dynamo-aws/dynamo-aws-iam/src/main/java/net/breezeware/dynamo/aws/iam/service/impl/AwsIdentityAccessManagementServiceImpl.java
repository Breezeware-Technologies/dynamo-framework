package net.breezeware.dynamo.aws.iam.service.impl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

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
import com.amazonaws.services.identitymanagement.model.EntityAlreadyExistsException;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.dynamo.aws.iam.dao.OrganizationIamUserCredentialRepository;
import net.breezeware.dynamo.aws.iam.entity.OrganizationIamUserCredential;
import net.breezeware.dynamo.aws.iam.service.api.AwsIdentityAccessManagementService;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.User;
import net.breezeware.dynamo.organization.service.api.OrganizationService;

@Slf4j
@Service
public class AwsIdentityAccessManagementServiceImpl implements AwsIdentityAccessManagementService {

    @Autowired
    OrganizationIamUserCredentialRepository organizationIamUserCredentialRepository;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    AmazonIdentityManagement awsIamuserConfiguration;

    /**
     * create IamUser using IAM SDK.
     * @param createUserRequest contains user name.
     * @return CreateUserResult contains IAM user metadata.
     */
    private CreateUserResult createIamUser(CreateUserRequest createUserRequest) {
        log.info("Entering createIamUser() {}", createUserRequest);

        CreateUserResult userResult = awsIamuserConfiguration.createUser(createUserRequest);

        log.info("Leaving createIamUser() {}", userResult);

        return userResult;
    }

    /**
     * Attach policy for an iam user using IAM SDK.
     * @param createUserResult contains IAM user metadata.
     * @return AttachUserPolicyResult.
     */
    private AttachUserPolicyResult attachPolicyForIamUser(CreateUserResult createUserResult) {
        log.info("Entering attachPolicyForIamUser() {}", createUserResult);
        AttachUserPolicyRequest attachUserPolicyRequest = new AttachUserPolicyRequest();
        attachUserPolicyRequest.setPolicyArn("arn:aws:iam::aws:policy/AmazonS3FullAccess");
        attachUserPolicyRequest.setUserName(createUserResult.getUser().getUserName());
        AttachUserPolicyResult attachUserPolicyResult = awsIamuserConfiguration
                .attachUserPolicy(attachUserPolicyRequest);
        log.info("Leaving attachPolicyForIamUser() {}", attachUserPolicyResult);

        return attachUserPolicyResult;

    }

    /**
     * provide security credential for an iam user using IAM SDK.
     * @param createUserResult contains IAM user metadata.
     * @return CreateAccessKeyResult.
     */
    private CreateAccessKeyResult createIamUserAccessKey(CreateUserResult createUserResult) {
        log.info("Entering createIamUserAccessKey() {}", createUserResult);
        CreateAccessKeyRequest createAccessKeyRequest = new CreateAccessKeyRequest();
        createAccessKeyRequest.setUserName(createUserResult.getUser().getUserName());
        CreateAccessKeyResult createAccessKeyResult = awsIamuserConfiguration.createAccessKey(createAccessKeyRequest);
        log.info("Leaving createIamUserAccessKey() {}", createAccessKeyResult);
        return createAccessKeyResult;
    }

    /**
     * build and save the OrganizationIamUserCredential entity.
     * @param organization          for which iam user created.
     * @param user                  iam user created for this user.
     * @param createUserResult      iam user metadata.
     * @param createAccessKeyResult iam user metadata with security credentials.
     * @return organizationIamUserCredential
     */
    private OrganizationIamUserCredential buildAndSaveOrganizationIamUserCredential(Organization organization,
            User user, CreateUserResult createUserResult, CreateAccessKeyResult createAccessKeyResult) {
        log.info(
                "Entering buildAndSaveOrganizationIamUserCredential() Organization{}, CreateUserResult {},CreateAccessKeyResult{}",
                organization, createUserResult, createAccessKeyResult);
        OrganizationIamUserCredential organizationIamUserCredential = new OrganizationIamUserCredential();
        organizationIamUserCredential.setAccessKey(createAccessKeyResult.getAccessKey().getAccessKeyId());
        organizationIamUserCredential.setSecertKey(createAccessKeyResult.getAccessKey().getSecretAccessKey());
        organizationIamUserCredential.setOrganization(organization);
        organizationIamUserCredential.setIamArn(createUserResult.getUser().getArn());
        organizationIamUserCredential.setCreatedDate(Instant.now());
        organizationIamUserCredential.setModifiedDate(Instant.now());
        organizationIamUserCredential.setUser(user);

        organizationIamUserCredential = saveOrganizationIamUserCredential(organizationIamUserCredential);
        log.info("Leaving buildAndSaveOrganizationIamUserCredential() {}", organizationIamUserCredential);
        return organizationIamUserCredential;
    }

    /**
     * save a organizationIamUserCredential.
     * @param organizationIamUserCredential OrganizationIamUserCredential instance.
     * @return OrganizationIamUserCredential.
     */
    private OrganizationIamUserCredential saveOrganizationIamUserCredential(
            OrganizationIamUserCredential organizationIamUserCredential) {
        log.info("Entering saveOrganizationIamUserCredential()");
        return organizationIamUserCredentialRepository.save(organizationIamUserCredential);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public Optional<OrganizationIamUserCredential> createIamUserWithAwsServicePolicy(Organization organization,
            User user) throws EntityAlreadyExistsException {
        log.info("Entering CreateIamUserWithAwsServicePolicy() Organization{} ,organizationAdminName{} ", organization,
                user);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        // String userName = user.getFirstName() + user.getLastName();
        // createUserRequest.setUserName(userName.trim().replaceAll("\\s", ""));
        createUserRequest.setUserName(UUID.randomUUID().toString());

        Optional<OrganizationIamUserCredential> organizationIamUserCredential = Optional.empty();

        CreateUserResult createUserResult = createIamUser(createUserRequest);

        attachPolicyForIamUser(createUserResult);
        CreateAccessKeyResult createAccessKeyResult = createIamUserAccessKey(createUserResult);

        organizationIamUserCredential = Optional.of(
                buildAndSaveOrganizationIamUserCredential(organization, user, createUserResult, createAccessKeyResult));

        log.info("Leaving CreateIamUserWithAwsServicePolicy() {}", organizationIamUserCredential);

        return organizationIamUserCredential;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public Optional<OrganizationIamUserCredential> retriveOrganizationIamUserCredential(Organization organization) {
        log.info("Entering retriveOrganizationIamUserCredential() Organization{},", organization);

        Optional<OrganizationIamUserCredential> optOrganizationIamUserCredential = organizationIamUserCredentialRepository
                .findByOrganization(organization);

        log.info("Leaving retriveOrganizationIamUserCredential()");
        return optOrganizationIamUserCredential;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public Optional<OrganizationIamUserCredential> retriveOrganizationIamUserCredential(User user) {
        log.info("Entering retriveOrganizationIamUserCredential() Organization{},", user);

        Optional<OrganizationIamUserCredential> optOrganizationIamUserCredential = organizationIamUserCredentialRepository
                .findByUser(user);

        log.info("Leaving retriveOrganizationIamUserCredential()");
        return optOrganizationIamUserCredential;
    }

}

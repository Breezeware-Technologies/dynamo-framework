package net.breezeware.dynamo.auth.organization.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.breezeware.dynamo.apps.dao.DynamoAppRepository;
import net.breezeware.dynamo.apps.entity.DynamoApp;
import net.breezeware.dynamo.apps.service.api.AppService;
import net.breezeware.dynamo.audit.aspectj.Auditable;
import net.breezeware.dynamo.auth.AuthenticationAuthorizationService;
import net.breezeware.dynamo.auth.organization.dao.OrganizationApiKeyMapRepository;
import net.breezeware.dynamo.auth.organization.dao.OrganizationAppMapRepository;
import net.breezeware.dynamo.auth.organization.dao.RoleAppMapRepository;
import net.breezeware.dynamo.auth.organization.dao.UserAppMapRepository;
import net.breezeware.dynamo.auth.organization.entity.ApiKey;
import net.breezeware.dynamo.auth.organization.entity.OrganizationApiKeyMap;
import net.breezeware.dynamo.auth.organization.entity.OrganizationAppMap;
import net.breezeware.dynamo.auth.organization.entity.RoleAppMap;
import net.breezeware.dynamo.auth.organization.entity.UserAppMap;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.User;
import net.breezeware.dynamo.organization.entity.UserRoleMap;
import net.breezeware.dynamo.organization.service.api.OrganizationService;
import net.breezeware.dynamo.util.usermgmt.CurrentUserDto;

@Service
@Profile("DBAuthentication")
public class AuthenticationAuthorizationServiceDbImpl implements AuthenticationAuthorizationService {

    Logger logger = LoggerFactory.getLogger(AuthenticationAuthorizationServiceDbImpl.class);

    @Value("${dynamo.userAuthenticationField}")
    private String userAuthenticationField;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    AppService appService;

    @Autowired
    OrganizationAppMapRepository organizationAppMapRepository;

    @Autowired
    OrganizationApiKeyMapRepository organizationApiKeyMapRepository;

    @Autowired
    RoleAppMapRepository roleAppMapRepository;

    @Autowired
    UserAppMapRepository userAppMapRepository;

    @Autowired
    DynamoAppRepository dynamoAppRepository;

    /**
     * {@inheritDoc}
     */
    @Transactional
    public CurrentUserDto getCurrentUserDto(String userId) {
        logger.info("Entering getCurrentUserDto(). userId = {}.", userId);

        User user = null;
        if (userAuthenticationField.equalsIgnoreCase("uniqueUserId")) {
            user = organizationService.findByUniqueUserIdIgnoreCase(userId);
        } else if (userAuthenticationField.equalsIgnoreCase("email")) {
            user = organizationService.findByEmailIgnoreCase(userId);
        } else if (userAuthenticationField.equalsIgnoreCase("uniqueUserIdOrEmail")) {
            user = organizationService.findByEmailIgnoreCase(userId);
            if (user == null) {
                user = organizationService.findByUniqueUserIdIgnoreCase(userId);
            }
        }

        CurrentUserDto userDto = null;
        if (user != null) {

            StringBuilder userRolesCsv = new StringBuilder();
            if (user.getUserRoleMap() != null && user.getUserRoleMap().size() > 0) {
                int index = 0;
                for (UserRoleMap urMap : user.getUserRoleMap()) {
                    userRolesCsv.append(urMap.getRole().getName());
                    index++;
                    if (index < user.getUserRoleMap().size()) {
                        userRolesCsv.append(",");
                    }
                }
            }

            userDto = new CurrentUserDto(user.getId(), String.valueOf(user.getOrganization().getId()), user.getEmail(),
                    user.getFirstName() + " " + user.getLastName(), userRolesCsv.toString(), user.getUserTimeZoneId());
        }

        logger.info("Exiting getCurrentUserDto(). CurrentUserDto = {}.", userDto);
        return userDto;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Apps Subscribed by Organization", argNames = "organizationId")
    public List<DynamoApp> getAppsSubscribedByOrganization(String organizationId) {
        logger.info("Entering getAppsSubscribedByOrganization() : organizationId = {}", organizationId);
        List<OrganizationAppMap> organizationAppMap = organizationAppMapRepository.findByOrganizationId(organizationId);
        List<DynamoApp> apps = new ArrayList<DynamoApp>();
        for (OrganizationAppMap organizationApp : organizationAppMap) {
            logger.debug("App ID to add = {}", organizationApp.getAppId());
            apps.add(appService.findAppById(Long.valueOf(organizationApp.getAppId())));
        }
        logger.info("ListOfDynamoApp : {}", apps);
        logger.info("Exiting getAppsSubscribedByOrganization()");
        return apps;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve If App is Subscribed by Organization", argNames = "organizationId, appId")
    public boolean isAppSubscribedByOrganization(String organizationId, String appId) {
        logger.info("Entering isAppSubscribedByOrganization() : organizationId = {}. appId = {}", organizationId,
                appId);
        boolean subscribed = false;

        OrganizationAppMap aaMap = organizationAppMapRepository.findByOrganizationIdAndAppId(organizationId, appId);
        if (aaMap != null) {
            subscribed = true;
        }

        logger.info("Entering isAppSubscribedByOrganization() : subscibed = {}.", subscribed);
        return subscribed;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Apps Available for Subscription", argNames = "organizationId")
    public List<DynamoApp> getAppsAvailableForSubscriptionForOrganization(String organizationId) {
        logger.info("Entering getAppsAvailableForSubscriptionForOrganization() : organizationId = {}", organizationId);

        List<DynamoApp> allApps = appService.findAllApps();
        logger.info("# of all apps = {}", allApps.size());

        List<DynamoApp> availableApps = new ArrayList<DynamoApp>();
        List<OrganizationAppMap> organizationAppMaps = organizationAppMapRepository
                .findByOrganizationId(organizationId);
        logger.info("# of subscribed apps = {}", organizationAppMaps.size());

        // creating a list of available apps
        for (DynamoApp dyApp : allApps) {
            boolean subscribed = false;
            for (OrganizationAppMap map : organizationAppMaps) {
                logger.debug("dApp ID = {}. map.getAppId = {}", dyApp.getId(), map.getAppId());
                if (dyApp.getId() == Long.valueOf(map.getAppId())) {
                    subscribed = true;
                    break;
                }
            }

            if (subscribed == false) {
                availableApps.add(dyApp);
            }
        }

        logger.info("Leaving getAppsAvailableForSubscriptionForOrganization(). # of apps available subscription = {}",
                availableApps.size());
        return availableApps;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Apps for Role", argNames = "roleId")
    public List<DynamoApp> getAppsForRole(String roleId) {
        logger.info("Entering getAppsForRole() : roleId = {}", roleId);
        List<RoleAppMap> roleAppMap = roleAppMapRepository.findByRoleId(roleId);
        List<DynamoApp> apps = new ArrayList<DynamoApp>();
        for (RoleAppMap roleApp : roleAppMap) {
            apps.add(appService.findAppById(Long.valueOf(roleApp.getAppId())));
        }
        logger.info("ListOfDynamoApp : {}", apps);
        logger.info("Exiting getAppsForRole()");
        return apps;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Apps for User", argNames = "userId")
    public List<DynamoApp> getAppsForUser(String userId) {
        logger.info("Entering getAppsForUser() : userId = {}", userId);
        List<UserAppMap> userAppMap = userAppMapRepository.findByUserId(userId);
        List<DynamoApp> apps = new ArrayList<DynamoApp>();
        for (UserAppMap userApp : userAppMap) {
            apps.add(appService.findAppById(Long.valueOf(userApp.getAppId())));
        }
        logger.info("ListOfDynamoApp : {}", apps);
        logger.info("Exiting getAppsForUser()");
        return apps;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Single App for Role", argNames = "appId, roleId")
    public DynamoApp getSingleAppForRole(String appId, String roleId) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Save Organization App Map", argNames = "organizationId, appIds")
    public List<OrganizationAppMap> saveOrganizationAppMap(String organizationId, List<String> appIds) {
        logger.info("Entering saveOrganizationAppMap : organizationId = {}, ListOfAppId = {} ", organizationId, appIds);
        List<OrganizationAppMap> maps = new ArrayList<OrganizationAppMap>();
        for (String appId : appIds) {
            OrganizationAppMap duplicateMap = organizationAppMapRepository.findByOrganizationIdAndAppId(organizationId,
                    appId);
            if (duplicateMap == null) {
                OrganizationAppMap organizationAppMap = new OrganizationAppMap();
                organizationAppMap.setOrganizationId(organizationId);
                organizationAppMap.setAppId(appId);
                organizationAppMap.setCreatedDate(Calendar.getInstance());
                organizationAppMap.setModifiedDate(Calendar.getInstance());
                maps.add(organizationAppMap);
            }
        }
        logger.info("OrganizationAppMaps = {} ", maps);
        logger.info("Exiting saveOrganizationAppMap");
        return organizationAppMapRepository.saveAll(maps);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Save Role App Map", argNames = "roleId, appIds")
    public List<RoleAppMap> saveRoleAppMap(String roleId, List<String> appIds) {
        logger.info("Entering saveRoleAppMap : roleId = {}, ListOfAppId = {} ", roleId, appIds);
        List<RoleAppMap> maps = new ArrayList<RoleAppMap>();
        for (String appId : appIds) {
            RoleAppMap duplicateMap = roleAppMapRepository.findByRoleIdAndAppId(roleId, appId);
            if (duplicateMap == null) {
                RoleAppMap roleAppMap = new RoleAppMap();
                roleAppMap.setRoleId(roleId);
                roleAppMap.setAppId(appId);
                roleAppMap.setCreatedDate(Calendar.getInstance());
                roleAppMap.setModifiedDate(Calendar.getInstance());
                maps.add(roleAppMap);
            }
        }
        logger.info("RoleAppMaps = {} ", maps);
        logger.info("Exiting saveRoleAppMap");
        return roleAppMapRepository.saveAll(maps);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve Role App Map", argNames = "organizationId, appId")
    public List<RoleAppMap> getRoleAppMap(String organizationId, String appId) {
        logger.info("Entering getRoleAppMap : organizationId = {}, appId = {}", organizationId, appId);

        List<RoleAppMap> maps = new ArrayList<RoleAppMap>();
        maps = roleAppMapRepository.findByOrganizationIdAndAppId(organizationId, appId);

        logger.info("Leaving getRoleAppMap(). # of mappings retrieved = {}", maps.size());
        return maps;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Reset Role App Map", argNames = "organizationId, appId, roleIds")
    public List<RoleAppMap> resetRoleAppMap(String organizationId, String appId, List<String> roleIds) {
        logger.info("Entering resetRoleAppMap : organizationId = {}, appId = {}", organizationId, appId);
        logger.info("roleIds = {} ", roleIds);
        List<RoleAppMap> maps = new ArrayList<RoleAppMap>();

        // delete existing mappings
        List<RoleAppMap> currentMaps = roleAppMapRepository.findByOrganizationIdAndAppId(organizationId, appId);
        logger.debug("Found {} current mappings. Deleting them", currentMaps.size());
        for (RoleAppMap map : currentMaps) {
            roleAppMapRepository.delete(map);
        }

        // insert new mappings
        for (String roleId : roleIds) {
            RoleAppMap roleAppMap = new RoleAppMap();
            roleAppMap.setOrganizationId(organizationId);
            roleAppMap.setRoleId(roleId);
            roleAppMap.setAppId(appId);
            roleAppMap.setCreatedDate(Calendar.getInstance());
            roleAppMap.setModifiedDate(Calendar.getInstance());
            maps.add(roleAppMap);
        }
        maps = roleAppMapRepository.saveAll(maps);
        logger.debug("Created {} new mappings.", maps.size());

        logger.info("Leaving resetRoleAppMap(). # of mappings created = {}", maps.size());
        return maps;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Save User App Map", argNames = "userId, appIds")
    public List<UserAppMap> saveUserAppMap(String userId, List<String> appIds) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Remove Organization App Map", argNames = "organizationId, appId")
    public boolean removeOrganizationAppMap(String organizationId, String appId) {
        logger.info("Entering removeOrganizationAppMap : organizationId = {}, appId = {} ", organizationId, appId);
        OrganizationAppMap organizationAppMap = organizationAppMapRepository
                .findByOrganizationIdAndAppId(organizationId, appId);
        if (organizationAppMap == null) {
            logger.info(
                    "Exiting removeOrganizationAppMap. No mapping exists for organizationId and appId. Not removing.");
            return false;
        } else {
            logger.info("Exiting removeOrganizationAppMap. Mapping exists for organizationId and appId. Removing.");
            organizationAppMapRepository.delete(organizationAppMap);
            return true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Remove Role App Map", argNames = "roleId, appId")
    public boolean removeRoleAppMap(String roleId, String appId) {
        logger.info("Entering removeRoleAppMap : roleId = {}, appId = {} ", roleId, appId);
        RoleAppMap roleAppMap = roleAppMapRepository.findByRoleIdAndAppId(roleId, appId);
        if (roleAppMap == null) {
            logger.info("Exiting removeRoleAppMap. No mapping exists for roleId and appId. Not removing.");
            return false;
        } else {
            logger.info("Exiting removeRoleAppMap. Mapping exists for roleId and appId. Removing.");
            roleAppMapRepository.delete(roleAppMap);
            return true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Remove User App Map", argNames = "userId, appId")
    public boolean removeUserAppMap(String userId, String appId) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Create API Key for Organization", argNames = "organizationId")
    public OrganizationApiKeyMap createOrganizationApiKey(long organizationId) {
        logger.info("Entering createOrganizationApiKey(). organizationId = {}", organizationId);

        // construct new OrganizationApiKeyMap entity
        OrganizationApiKeyMap okMap = new OrganizationApiKeyMap();

        // retrieve organization by its ID
        Organization organization = organizationService.findOrganizationById(organizationId);

        if (organization != null) {
            ApiKey apiKey = new ApiKey();
            apiKey.setCreatedDate(Instant.now());
            apiKey.setModifiedDate(Instant.now());
            apiKey.setStatus(ApiKey.KEY_STATUS_ACTIVE);
            apiKey.setValue(UUID.randomUUID());

            okMap.setApiKey(apiKey);
            okMap.setCreatedDate(Instant.now());
            okMap.setModifiedDate(Instant.now());
            okMap.setOrganization(organization);

            // save the entity in DB
            okMap = organizationApiKeyMapRepository.save(okMap);
        } else {
            logger.info("Organization with ID = {} could not be found. Returning empty OrganizationApiKeyMap. ",
                    organizationId);
        }

        logger.info(
                "Leaving createOrganizationApiKey(). API Key with ID = {} and "
                        + "value = {} created and stored for Organization.",
                okMap.getApiKey().getId(), okMap.getApiKey().getValue());
        return okMap;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve API Key By Value", argNames = "apiKeyValue")
    public Optional<OrganizationApiKeyMap> retrieveOrganizationApiKeyByKeyValue(UUID apiKeyValue) {
        logger.info("Entering retrieveOrganizationApiKeyByKeyValue(). apiKeyValue = {}", apiKeyValue);

        OrganizationApiKeyMap okMap = organizationApiKeyMapRepository.findByApiKeyValue(apiKeyValue);
        logger.info("Leaving retrieveOrganizationApiKeyByKeyValue(). okMap = {}", okMap);

        return Optional.ofNullable(okMap);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve API Keys for Organization", argNames = "organizationId")
    public Optional<List<OrganizationApiKeyMap>> retrieveOrganizationApiKeysForOrganization(long organizationId) {
        logger.info("Entering retrieveOrganizationApiKeysForOrganization(). organizationId = {}", organizationId);

        List<OrganizationApiKeyMap> okMapList = organizationApiKeyMapRepository.findByOrganizationId(organizationId);
        logger.info("Leaving retrieveOrganizationApiKeysForOrganization().");

        return Optional.ofNullable(okMapList);
    }
}
package net.breezeware.dynamo.config.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import net.breezeware.dynamo.audit.aspectj.Auditable;
import net.breezeware.dynamo.config.dao.AppPropertyRepository;
import net.breezeware.dynamo.config.entity.AppProperty;
import net.breezeware.dynamo.config.service.api.AppConfigService;

@Service
public class AppConfigServiceImpl implements AppConfigService {

    Logger logger = LoggerFactory.getLogger(AppConfigServiceImpl.class);

    @Autowired
    AppPropertyRepository appPropertyRepository;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve App Properties", argNames = "")
    public List<AppProperty> getAppProperties() {
        logger.info("Entering getAppProperties()");

        List<AppProperty> properties = appPropertyRepository.findAll();
        logger.info("Leaving getAppProperties(). # of properties found = {}", properties.size());

        return properties;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve App Properties", argNames = "application")
    public List<AppProperty> findByApplication(String application) {
        logger.info("Entering findByApplication()");

        List<AppProperty> properties = appPropertyRepository.findByApplication(application);
        logger.info("Leaving findByApplication(). # of properties found = {}", properties.size());

        return properties;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve App Properties", argNames = "application, profile")
    public List<AppProperty> findByApplicationAndProfile(String application, String profile) {
        logger.info("Entering findByApplicationAndProfile()");

        List<AppProperty> properties = appPropertyRepository.findByApplicationAndProfile(application, profile);
        logger.info("Leaving findByApplicationAndProfile(). # of properties found = {}", properties.size());

        return properties;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve App Properties", argNames = "application, profile, label")
    public List<AppProperty> findByApplicationAndProfileAndLabel(String application, String profile, String label) {
        logger.info("Entering findByApplicationAndProfileAndLabel()");

        List<AppProperty> properties = appPropertyRepository.findByApplicationAndProfileAndLabel(application, profile,
                label);
        logger.info("Leaving findByApplicationAndProfileAndLabel(). # of properties found = {}", properties.size());

        return properties;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve App Properties", argNames = "application, profile, label, key")
    public AppProperty findByApplicationAndProfileAndLabelAndKey(String application, String profile, String label,
            String key) {
        logger.info("Entering findByApplicationAndProfileAndLabelAndKey()");

        AppProperty property = appPropertyRepository.findByApplicationAndProfileAndLabelAndKey(application, profile,
                label, key);
        logger.info("Leaving findByApplicationAndProfileAndLabel(). Property = {}", property);

        return property;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve App Property", argNames = "id")
    public AppProperty getAppPropertyById(long id) {
        logger.info("Entering getAppPropertyById(). Id = {}", id);

        AppProperty item = appPropertyRepository.getOne(id);
        logger.info("Leaving getAppPropertyById(). Item = {}", item);

        return item;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve App Property", argNames = "predicate, pageable")
    public Page<AppProperty> findAppProperties(Predicate predicate, Pageable pageable) {
        logger.info("Entering findAppProperties()");

        Page<AppProperty> propertiesPage = appPropertyRepository.findAll(predicate, pageable);

        logger.info("Leaving findAppProperties(). # of paged items = {}", propertiesPage.getSize());
        return propertiesPage;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Save App Property", argNames = "config")
    public AppProperty saveAppProperty(AppProperty config) throws Exception {
        logger.info("Entering saveAppProperty(), Config = {}", config);

        config = appPropertyRepository.save(config);

        logger.info("Leaving saveAppProperty()");
        return config;
    }
}
package net.breezeware.dynamo.apps.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;

import net.breezeware.dynamo.apps.dao.AppFeatureRepository;
import net.breezeware.dynamo.apps.dao.DynamoAppRepository;
import net.breezeware.dynamo.apps.entity.AppFeature;
import net.breezeware.dynamo.apps.entity.DynamoApp;
import net.breezeware.dynamo.apps.service.api.AppService;
import net.breezeware.dynamo.audit.aspectj.Auditable;

@Service
public class AppServiceImpl implements AppService {

    @Autowired
    DynamoAppRepository dynamoAppRepository;

    @Autowired
    AppFeatureRepository appFeatureRepository;

    Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retireve All Apps", argNames = "predicate, pageable")
    public Page<DynamoApp> findAllApps(Predicate predicate, Pageable pageable) {
        logger.info("Entering findAllApps Method()");

        logger.info("Leaving findAllApps Method()");
        return dynamoAppRepository.findAll(predicate, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve All Apps", argNames = "")
    public List<DynamoApp> findAllApps() {
        return dynamoAppRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve App", argNames = "appId")
    public DynamoApp findAppById(long appId) {
        logger.info("Entering findAppById Method(). App ID to find = {}", appId);

        DynamoApp app = dynamoAppRepository.getOne(appId);
        if (app != null) {
            logger.info("Found app with ID = {} and name = {}", app.getId(), app.getName());

            if (app.getAppFeatures() != null) {
                logger.info("# of features found in app = {}", app.getAppFeatures().size());
            }

        } else {
            logger.info("Could not find app with ID = {}", appId);
        }

        logger.info("Leaving findAppById Method()");
        return app;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve App", argNames = "appName")
    public DynamoApp findByAppName(String appName) {
        logger.info("Entering findByAppName, AppName ={}", appName);
        logger.info("Leaving findByAppName");
        return dynamoAppRepository.findByNameIgnoreCase(appName);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve App Feature", argNames = "featureName")
    public AppFeature findByFeatureName(String featureName) {
        logger.info("Entering findByAppFeature, FeatureName ={}", featureName);
        logger.info("Leaving findByAppFeature");
        return appFeatureRepository.findByNameIgnoreCase(featureName);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Save App", argNames = "app")
    public DynamoApp saveDynamoApp(DynamoApp app) throws Exception {
        logger.info("Entering saveDynamoApp Method(), App = {}", app);

        // handle case where there are no app features. Throw exception
        List<AppFeature> features = app.getAppFeatures();
        if (features == null || features != null && features.size() == 0) {
            logger.error("Dynamo app does not contain any features. Throwing exception");
            throw new Exception("Dynamo app should contain a minimum of one feature. Please try again.");
        }

        logger.info("# of app features to save = {}", features.size());

        long savedAppId = -1;
        // app already exists - edit app
        if (app.getId() > 0) {
            savedAppId = app.getId();

            List<Long> editedAppFeatures = new ArrayList<Long>();
            for (AppFeature feature : features) {
                if (feature.getId() <= 0) {
                    // new app feature
                    feature.setAppId(savedAppId);
                    feature.setCreatedDate(Calendar.getInstance());
                    feature.setModifiedDate(Calendar.getInstance());
                    feature.setStatus("active");
                } else {
                    // existing app feature
                    feature.setAppId(savedAppId);
                    feature.setModifiedDate(Calendar.getInstance());
                    feature.setStatus("active");
                }
            }
            app.setAppFeatures(features);
            app = dynamoAppRepository.save(app);

            // delete app features not part of the edit
            appFeatureRepository.deleteOrphanedAppFeatures();
        } else {
            // app does not exist, create new app
            app.setAppFeatures(new ArrayList<AppFeature>());
            app = dynamoAppRepository.save(app);

            logger.info("App details = {}", app);

            savedAppId = app.getId();
            logger.info("savedAppId = {}", savedAppId);

            for (AppFeature feature : features) {
                feature.setAppId(savedAppId);
                feature.setCreatedDate(Calendar.getInstance());
                feature.setModifiedDate(Calendar.getInstance());
                feature.setStatus("active");

                logger.info("Feature = {}", feature);
            }
            app.setAppFeatures(features);
            app = dynamoAppRepository.save(app);
        }

        logger.info("Leaving saveDynamoApp Method(). ID of saved app = {}", savedAppId);

        return app;
    }

    /**
     * {@inheritDoc}
     */
    // FIXME: revisit logic
    @Transactional
    @Auditable(event = "Remove Features From App", argNames = "featureId")
    public boolean removeFeaturesFromApp(List<Long> featureId) {
        logger.info("Entering removeFeaturesFromApp, FeatureIds = {}", featureId);
        for (long id : featureId) {
            AppFeature feature = appFeatureRepository.getOne(id);
            feature.setAppId(-1);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Update App Status", argNames = "appId, appStatus")
    public boolean updateAppStatus(long appId, String appStatus) {
        logger.info("Entering updateAppStatus Method(), AppId = {} Status = {}", appId, appStatus);
        DynamoApp app = dynamoAppRepository.getOne(appId);
        logger.info("App = {}", app);
        logger.info("Leaving updateAppStatus Method()");
        if (app != null) {
            app.setStatus(appStatus);
            dynamoAppRepository.save(app);
            return true;
        } else {
            return false;
        }
    }

    // FIXME: revisit logic
    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Update App Feature Status", argNames = "featureStatus")
    public void updateFeaturesStatus(Map<Long, String> featureStatus) {
        logger.info("Entering updateFeaturesStatus Method(), FeatureStatus = {} ", featureStatus);
        for (Map.Entry<Long, String> map : featureStatus.entrySet()) {
            AppFeature feature = appFeatureRepository.getOne((long) map.getKey());
            logger.info("Feature = {}", feature);
            if (feature != null) {
                feature.setStatus(map.getValue());
            }
        }
        logger.info("Leaving updateFeaturesStatus Method()");
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Remove App with Features", argNames = "appId")
    public boolean removeAppWithFeatures(long appId) {
        logger.info("Entering RemoveAppWithFeatures, AppId = {}", appId);
        DynamoApp app = dynamoAppRepository.getOne(appId);
        logger.info("App = {}", app);
        if (app == null) {
            logger.info("Leaving RemoveAppWithFeatures");
            return false;
        } else {
            List<AppFeature> features = app.getAppFeatures();
            logger.info("Features = {}", features);
            for (AppFeature feature : features) {
                appFeatureRepository.delete(feature);
            }
            dynamoAppRepository.delete(app);
            logger.info("Leaving RemoveAppWithFeatures");
            return true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Auditable(event = "Retrieve All Features", argNames = "")
    public List<AppFeature> findAllFeatures() {
        return appFeatureRepository.findAll();
    }

}

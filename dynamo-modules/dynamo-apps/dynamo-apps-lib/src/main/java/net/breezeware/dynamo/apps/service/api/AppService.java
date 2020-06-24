package net.breezeware.dynamo.apps.service.api;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Predicate;

import net.breezeware.dynamo.apps.entity.AppFeature;
import net.breezeware.dynamo.apps.entity.DynamoApp;

/**
 * Interface to manage aspects like application, feature and application-feature
 * mapping.
 */
public interface AppService {
    /**
     * Retrieves all apps.
     * 
     * @return List of apps if available, else empty list.
     */
    Page<DynamoApp> findAllApps(Predicate predicate, Pageable pageable);

    /**
     * 
     * Retrieves an application by its id.
     * 
     * @param appId
     *            Application identity.
     * 
     * @return Application if available, else null.
     */
    DynamoApp findAppById(long appId);

    /**
     * Retrieves an application by its name.
     * 
     * @param appName
     * @return Application if available, else null.
     */
    DynamoApp findByAppName(String appName);

    /**
     * Retrieves an application feature by its name.
     * 
     * @param featureName
     * @return Application feature if available, else null.
     */
    AppFeature findByFeatureName(String featureName);

    /**
     * Persists an application along with its features.
     * 
     * @param app
     * @return Application with App ID set if successfully saved, else original
     *         Application without App ID set.
     */
    DynamoApp saveDynamoApp(DynamoApp app) throws Exception;

    /**
     * FIXME: Removes features from an app.
     * 
     * @param appId
     *            Id to uniquely identify an app.
     * @param featureId
     *            List of feature IDs to removed
     * @return Returns true if all specified features are removed or if features are
     *         not available.
     */
    boolean removeFeaturesFromApp(List<Long> featureId);

    /**
     * Update app status.
     * 
     * @param appId
     *            Id to uniquely identify an app.
     * @param appStatus
     * @return Returns true if updated else false.
     */
    boolean updateAppStatus(long appId, String appStatus);

    /**
     * Update a list of feature status.
     * 
     * @param featureStatus
     *            Map of featureId and featureStatus.
     */
    void updateFeaturesStatus(Map<Long, String> featureStatus);

    /**
     * Removes an app and its features from database.
     * 
     * @param appId
     * @return Returns true if removed else false.
     */
    boolean removeAppWithFeatures(long appId);

    /**
     * Retrieves all apps.
     * 
     * @return List of apps.
     */
    List<DynamoApp> findAllApps();

    /**
     * Retrieves all features.
     * 
     * @return List of features.
     */
    List<AppFeature> findAllFeatures();

}
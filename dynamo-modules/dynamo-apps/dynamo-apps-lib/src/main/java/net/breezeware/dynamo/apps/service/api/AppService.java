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
     * Retrieve all apps.
     * @param predicate the interface for Boolean typed expressions
     * @param pageable  the interface for pagination information
     * @return a sublist representing a single page in the paginated list
     */
    Page<DynamoApp> findAllApps(Predicate predicate, Pageable pageable);

    /**
     * Retrieve all apps.
     * @return a List of apps.
     */
    List<DynamoApp> findAllApps();

    /**
     * Retrieve an application by its id.
     * @param appId the ID to uniquely identify an app
     * @return the DynamoApp identified by its ID
     */
    DynamoApp findAppById(long appId);

    /**
     * Retrieve an application by its name.
     * @param appName the name to uniquely identify an app
     * @return the DynamoApp identified by its name
     */
    DynamoApp findByAppName(String appName);

    /**
     * Retrieve an application feature by its name.
     * @param featureName the name to uniquely identify an AppFeature
     * @return the AppFeature identified by its name
     */
    AppFeature findByFeatureName(String featureName);

    /**
     * Save an application along with its features.
     * @param app the DynamoApp to be saved
     * @return the DynamoApp that was saved
     * @throws Exception the excaption thrown if there was an error while saving the
     *                   app
     */
    DynamoApp saveDynamoApp(DynamoApp app) throws Exception;

    /**
     * Remove the features from an app.
     * @param featureId the list of feature IDs that has to be removed
     * @return true if all the features were successfully removed and false
     *         otherwise
     */
    boolean removeFeaturesFromApp(List<Long> featureId);

    /**
     * Update an app's status.
     * @param appId     the Id to uniquely identify an app.
     * @param appStatus the status that will be set to the app
     * @return returns true if updated else false.
     */
    boolean updateAppStatus(long appId, String appStatus);

    /**
     * Update a list of feature status.
     * @param featureStatus Map of featureId and featureStatus.
     */
    void updateFeaturesStatus(Map<Long, String> featureStatus);

    /**
     * Removes an app and its features from database.
     * @param appId the ID to uniquely identify an app
     * @return returns true if removed else false.
     */
    boolean removeAppWithFeatures(long appId);

    /**
     * Retrieves all features.
     * @return List of features.
     */
    List<AppFeature> findAllFeatures();
}
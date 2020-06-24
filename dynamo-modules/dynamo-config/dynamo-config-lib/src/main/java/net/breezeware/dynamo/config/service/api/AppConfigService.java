package net.breezeware.dynamo.config.service.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Predicate;

import net.breezeware.dynamo.config.entity.AppProperty;

/**
 * Interface to manage an Application's configuration items
 * 
 *
 */
public interface AppConfigService {

    /**
     * Retrieves all the properties irrespective of application.
     * 
     * Sample entry in the DB: application: dynamoorg | profile:dev | label:master |
     * key:enableAuditing | value:true
     *
     * @return List of app properties if one or more exists, else empty list.
     */
    List<AppProperty> getAppProperties();

    /**
     * Retrieves all properties for a given application.
     * 
     * Sample entry in the DB: application: dynamoorg | profile:dev | label:master |
     * key:enableAuditing | value:true
     * 
     * @param application
     *            Application name, eg: dynamoorg
     * @return List of app properties if one or more exists, else empty list.
     */
    List<AppProperty> findByApplication(String application);

    /**
     * Retrieves all properties defined for application and profile combination.
     * 
     * Sample entry in the DB: application: dynamoorg | profile:dev | label:master |
     * key:enableAuditing | value:true
     * 
     * @param application
     *            Application name, eg: dynamoorg
     * @param profile
     *            Profile name, eg: dev
     * @return List of app properties if one or more exists, else empty list.
     */
    List<AppProperty> findByApplicationAndProfile(String application, String profile);

    /**
     * Retrieves all properties defined for application, profile and label
     * combination.
     * 
     * Sample entry in the DB: application: dynamoorg | profile:dev | label:master |
     * key:enableAuditing | value:true
     * 
     * @param application
     *            Application name, eg: dynamoorg
     * @param profile
     *            Profile name, eg: dev
     * @param label
     *            Label name, default is 'master'
     * @return List of app properties if one or more exists, else empty list.
     */
    List<AppProperty> findByApplicationAndProfileAndLabel(String application, String profile, String label);

    /**
     * Retrieves a single property in an application identified by its key and other
     * attributes.
     * 
     * Sample entry in the DB: application: dynamoorg | profile:dev | label:master |
     * key:enableAuditing | value:true
     * 
     * @param application
     *            Application name, eg: dynamoorg
     * @param profile
     *            Profile name, eg: dev
     * @param label
     *            Label name, default is 'master'
     * @param key
     *            Key name, eg: enableAuditing
     * @return
     */
    AppProperty findByApplicationAndProfileAndLabelAndKey(String application, String profile, String label, String key);

    /**
     * 
     * Retrieves an app property by its id.
     * 
     * @param id
     *            app property id.
     * @return item.
     */
    AppProperty getAppPropertyById(long id);

    /**
     * Retrieves all App Properties
     * 
     * @return List of app properties according to criteria, if available, else
     *         empty list.
     */
    Page<AppProperty> findAppProperties(Predicate predicate, Pageable pageable);

    /**
     * Persists an configuration.
     * 
     * @param config
     * @return AppProperty.
     */
    AppProperty saveAppProperty(AppProperty config) throws Exception;
}
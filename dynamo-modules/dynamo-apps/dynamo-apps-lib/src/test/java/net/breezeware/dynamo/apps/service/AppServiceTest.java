package net.breezeware.dynamo.apps.service;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flywaydb.test.annotation.FlywayTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.querydsl.core.BooleanBuilder;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import net.breezeware.dynamo.apps.TestApplication;
import net.breezeware.dynamo.apps.dao.AppFeatureRepository;
import net.breezeware.dynamo.apps.entity.AppFeature;
import net.breezeware.dynamo.apps.entity.DynamoApp;
import net.breezeware.dynamo.apps.service.api.AppService;

/**
 * Class to test App Service APIs. This test class uses an
 * 'AutoConfigureEmbeddedDatabase' for testing with an embedded Postgres DB.
 * 'FlywayTest' is used to configure the schema and data in the embedded
 * database. FlywayTest properties are stored in test-application.properties.
 *
 */
@ContextConfiguration(classes = TestApplication.class)
@AutoConfigureTestDatabase(replace = NONE)
@AutoConfigureEmbeddedDatabase
@FlywayTest
public class AppServiceTest extends AbstractTestNGSpringContextTests {

    Logger logger = LoggerFactory.getLogger(AppServiceTest.class);

    @Autowired
    AppService appService;

    @Autowired
    AppFeatureRepository appFeatureRepository;

    @Test
    public void findAllAppsTestWithPredicateAndPageable() {
        logger.info("Entering findAllAppsTestWithPredicateAndPageable()");

        // Pageable entity
        int page = 0;
        int size = 10;
        PageRequest pageRequest = new PageRequest(page, size);

        Page<DynamoApp> apps = appService.findAllApps(new BooleanBuilder(), pageRequest);
        logger.info("# of Apps = {}", apps.getContent().size());

        Assert.assertEquals(apps.getContent().size(), 3);
        logger.info("Leaving findAllAppsTestWithPredicateAndPageable()");
    }

    @Test
    public void saveDynamoAppTest() {
        logger.info("Entering saveDynamoAppTest()");

        DynamoApp app = new DynamoApp();
        app.setCreatedDate(Calendar.getInstance());
        app.setModifiedDate(Calendar.getInstance());
        app.setDescription("Dummy App for testing!");
        app.setName("dummy_app_1");
        app.setStatus("active");

        List<AppFeature> features = new ArrayList<AppFeature>();
        AppFeature feature1 = new AppFeature();
        feature1.setCreatedDate(Calendar.getInstance());
        feature1.setModifiedDate(Calendar.getInstance());
        feature1.setDescription("For Testing1!");
        feature1.setName("dummy_feature_1");
        feature1.setStatus("active");

        AppFeature feature2 = new AppFeature();
        feature2.setCreatedDate(Calendar.getInstance());
        feature2.setModifiedDate(Calendar.getInstance());
        feature2.setDescription("For Testing!");
        feature2.setName("dummy_feature_2");
        feature2.setStatus("active");

        features.add(feature1);
        features.add(feature2);

        // AppFeature feature1 = appFeatureRepository.findByFeatureId("create_ques");
        // AppFeature feature2 = appFeatureRepository.findByFeatureId("imp_fhir");
        // List<AppFeature> features = new ArrayList<AppFeature>();
        // features.add(feature1);
        // features.add(feature2);

        app.setAppFeature(features);

        app = appService.saveDynamoApp(app);

        logger.info("Leaving saveDynamoAppTest()");
    }

    // @Test
    public void findAppByIdTest() {
        logger.info("Entering findAppByIdTest()");
        long appId = 1;
        DynamoApp app = appService.findAppById(appId);
        logger.info("App = {} ", app);
        logger.info("Leaving findAppByIdTest().");
    }

    // @Test
    public void findAllAppsTest() {
        logger.info("Entering findAllAppsTest()");
        List<DynamoApp> apps = appService.findAllApps();

        // appService.findAllApps(predicate, pageable);

        logger.info("Apps = {}", apps);
        logger.info("Leaving findAllAppsTest()");
    }

    // @Test
    public void findByAppNameTest() {
        logger.info("Entering findByAppNameTest");
        DynamoApp app = appService.findByAppName("Fast Healthcare Inter-operability Resources");
        logger.info("App = {}", app);
        logger.info("Leaving findByAppNameTest");
    }

    // @Test
    public void findByFeatureNameTest() {
        logger.info("Entering findByFeatureNameTest");
        AppFeature feature = appService.findByFeatureName("Import FHIR");
        logger.info("App = {}", feature);
        logger.info("Leaving findByFeatureNameTest");
    }

    // @Test
    public void addFeaturesToAppTest() {
        logger.info("Entering addFeaturesToAppTest()");
        List<AppFeature> features = new ArrayList<AppFeature>();
        AppFeature feature1 = new AppFeature();
        feature1.setCreatedDate(Calendar.getInstance());
        feature1.setModifiedDate(Calendar.getInstance());
        feature1.setDescription("For Testing1!");
        feature1.setName("dummy_feature_3");
        feature1.setStatus("active");

        AppFeature feature2 = new AppFeature();
        feature2.setCreatedDate(Calendar.getInstance());
        feature2.setModifiedDate(Calendar.getInstance());
        feature2.setDescription("For Testing!");
        feature2.setName("dummy_feature_4");
        feature2.setStatus("active");

        features.add(feature1);
        features.add(feature2);

        appService.addFeaturesToApp(6, features);
        // logger.info("No of features in app = {} ", app.getAppFeature().size());
        logger.info("Leaving addFeaturesToAppTest()");

    }

    // @Test
    public void updateAppStatusTest() {
        logger.info("Entering updateAppStatusTest");
        appService.updateAppStatus(6, "deleted");
        logger.info("Leaving updateAppStatusTest");

    }

    // @Test
    public void updateFeaturesStatusTest() {
        logger.info("Entering updateFeaturesStatusTest");
        Map<Long, String> featureStatus = new HashMap<Long, String>();
        featureStatus.put((long) 26, "inactive");
        featureStatus.put((long) 27, "inactive");
        appService.updateFeaturesStatus(featureStatus);
        logger.info("Leaving updateFeaturesStatusTest");
    }

    // @Test
    public void removeAppWithFeaturesTest() {
        logger.info("Entering removeAppWithFeaturesTest");
        appService.removeAppWithFeatures(8);
        logger.info("Leaving removeAppWithFeaturesTest");
    }

    // @Test
    public void removeFeaturesFromAppTest() {
        logger.info("Entering removeFeaturesFromAppTest");

        logger.info("Leaving removeFeaturesFromAppTest");
    }
    // // @Test
    // public void updateAppTest() {
    // logger.info("Entering updateAppTest");
    // logger.info("Leaving updateAppTest");
    //
    // }
    //
    // // @Test
    // public void removeFeaturesFromAppTest() {
    // logger.info("Entering removeFeaturesFromAppTest");
    // logger.info("Leaving removeFeaturesFromAppTest");
    //
    // }
    //
    // @Test
    // public void removeFeatureTest() {
    // logger.info("Entering removeFeatureTest");
    // appService.removeFeature("save_feature");
    // logger.info("Leaving removeFeatureTest");
    //
    // }
}
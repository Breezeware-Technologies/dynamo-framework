package net.breezeware.config.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.querydsl.core.BooleanBuilder;

import net.breezeware.config.TestApplication;
import net.breezeware.dynamo.config.entity.AppProperty;
import net.breezeware.dynamo.config.service.api.AppConfigService;

@ContextConfiguration(classes = TestApplication.class)
public class AppConfigServiceTest extends AbstractTestNGSpringContextTests {

    Logger logger = LoggerFactory.getLogger(AppConfigServiceTest.class);

    @Autowired
    AppConfigService appConfigService;

    @Test
    public void findAppPropertiesTest() {
        logger.info("Entering findAppPropertiesTest()");

        // Pageable entity
        int page = 0;
        int size = 10;
        PageRequest pageRequest = new PageRequest(page, size);

        Page<AppProperty> appPropertiesPage = appConfigService.findAppProperties(new BooleanBuilder(), pageRequest);
        logger.info("Total # of app properties = {}. # of items retrieved = {}", appPropertiesPage.getTotalElements(),
                appPropertiesPage.getContent().size());

        // Assert.assertEquals(auditItems.getContent().size(), 3);
        logger.info("Leaving findAppPropertiesTest().");
    }

    @Test
    public void getAppPropertyByIdTest() {
        logger.info("Entering getAppPropertyByIdTest()");
        long itemId = 1;
        AppProperty ap = appConfigService.getAppPropertyById(itemId);
        logger.info("App Property = {} ", ap);
        logger.info("Leaving getAppPropertyByIdTest().");
    }
}
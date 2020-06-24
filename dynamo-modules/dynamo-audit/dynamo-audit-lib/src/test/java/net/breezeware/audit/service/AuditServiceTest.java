package net.breezeware.audit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.querydsl.core.BooleanBuilder;

import net.breezeware.audit.TestApplication;
import net.breezeware.dynamo.audit.entity.AuditItem;
import net.breezeware.dynamo.audit.service.api.AuditService;

@ContextConfiguration(classes = TestApplication.class)
public class AuditServiceTest extends AbstractTestNGSpringContextTests {

    Logger logger = LoggerFactory.getLogger(AuditServiceTest.class);

    @Autowired
    AuditService auditService;

    @Test
    public void findAllAuditItemsTest() {
        logger.info("Entering findAllAuditItemsTest()");

        // Pageable entity
        int page = 0;
        int size = 10;
        PageRequest pageRequest = new PageRequest(page, size);

        Page<AuditItem> auditItemsPage = auditService.findAllAuditItems(new BooleanBuilder(), pageRequest);
        logger.info("Total # of audit items = {}. # of items retrieved = {}", auditItemsPage.getTotalElements(),
                auditItemsPage.getContent().size());

        // Assert.assertEquals(auditItems.getContent().size(), 3);
        logger.info("Leaving findAllAuditItemsTest().");
    }

    @Test
    public void getItemByIdTest() {
        logger.info("Entering getItemByIdTest()");
        long itemId = 1;
        AuditItem item = auditService.getItemById(itemId);
        logger.info("Audit Item = {} ", item);
        logger.info("Leaving getItemByIdTest().");
    }
}
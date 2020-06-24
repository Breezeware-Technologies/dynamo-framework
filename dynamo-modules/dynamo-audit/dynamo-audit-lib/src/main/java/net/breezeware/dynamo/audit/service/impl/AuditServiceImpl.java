package net.breezeware.dynamo.audit.service.impl;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.PredicateOperation;
import com.querydsl.core.types.dsl.BooleanOperation;

import net.breezeware.dynamo.audit.dao.AuditItemRepository;
import net.breezeware.dynamo.audit.entity.AuditItem;
import net.breezeware.dynamo.audit.entity.QAuditItem;
import net.breezeware.dynamo.audit.service.api.AuditService;

@Service
public class AuditServiceImpl implements AuditService {

    Logger logger = LoggerFactory.getLogger(AuditServiceImpl.class);

    @Autowired
    AuditItemRepository auditItemRepository;

    @Override
    @Transactional
    // @Auditable(event = "Save Audit Item", argNames = "item")
    public AuditItem saveItem(AuditItem item) {
        logger.info("Entering saveItem(). Item = {}", item);

        item = auditItemRepository.save(item);
        logger.info("Leaving saveItem(). Item = {}", item);

        return item;
    }

    @Override
    @Transactional
    // @Auditable(event = "Retrieve Audit Item", argNames = "id")
    public AuditItem getItemById(long id) {
        logger.info("Entering getItemById(). Id = {}", id);

        AuditItem item = auditItemRepository.getOne(id);
        logger.info("Leaving getItemById(). Item = {}", item);

        return item;
    }

    @Override
    @Transactional
    // @Auditable(event = "Retrieve All Audit Items", argNames = "predicate,
    // pageable")
    public Page<AuditItem> findAllAuditItems(Predicate predicate, Pageable pageable) {
        logger.info("Entering findAllAuditItems()");

        Page<AuditItem> auditItemsPage = new PageImpl<AuditItem>(new ArrayList<AuditItem>());

        if (predicate != null) {
            auditItemsPage = auditItemRepository.findAll(predicate, pageable);
        } else {
            auditItemsPage = auditItemRepository.findAll(pageable);
        }

        logger.info("Leaving findAllAuditItems(). # of paged items = {}", auditItemsPage.getSize());
        return auditItemsPage;
    }

    @Override
    @Transactional
    public Page<AuditItem> findAuditItemsForOrganization(long organizationId, Predicate predicate, Pageable pageable) {
        logger.info("Entering findAuditItemsForOrganization()");

        // add organization to the predicate before querying for groups
        BooleanBuilder bb = new BooleanBuilder();
        if (predicate != null) {
            logger.info("Predicate class = {}", predicate.getClass());
            if (predicate.getClass().equals(BooleanBuilder.class)) {
                bb = (BooleanBuilder) predicate;
                if (bb.getValue() == null) {
                    bb = new BooleanBuilder();
                }
                bb.and(QAuditItem.auditItem.organizationId.eq(organizationId));
            } else if (predicate.getClass().equals(BooleanOperation.class)) {
                BooleanOperation bo = (BooleanOperation) predicate;
                bb = new BooleanBuilder();
                bb.and(bo);
                bb.and(QAuditItem.auditItem.organizationId.eq(organizationId));
            } else if (predicate.getClass().equals(PredicateOperation.class)) {
                PredicateOperation po = (PredicateOperation) predicate;
                bb = new BooleanBuilder();
                bb.and(po);
                bb.and(QAuditItem.auditItem.organizationId.eq(organizationId));
            }
        } else {
            logger.info("Predicate is null");
            bb.and(QAuditItem.auditItem.organizationId.eq(organizationId));
        }

        Page<AuditItem> itemsPage = auditItemRepository.findAll(bb, pageable);
        logger.info("Leaving findAuditItemsForOrganization(). # of paged items = {}", itemsPage.getSize());
        return itemsPage;
    }
}
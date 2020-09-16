package net.breezeware.dynamo.audit.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Predicate;

import net.breezeware.dynamo.audit.entity.AuditItem;

/**
 * Interface to manage audit services.
 * @author gowtham
 */
public interface AuditService {
    /**
     * Persists an audit item.
     * @param item the audit item.
     * @return saved entity.
     */
    AuditItem saveItem(AuditItem item);

    /**
     * Retrieves an audit item by its id.
     * @param id the item id.
     * @return item.
     */
    AuditItem getItemById(long id);

    /**
     * Retrieves audit items from all organizations.
     * @param predicate the interface for Boolean typed expressions. Supports
     *                  binding of HTTP parameters to QueryDSL predicate
     * @param pageable  the interface for pagination information
     * @return List of audit items if available, else empty list.
     */
    Page<AuditItem> findAllAuditItems(Predicate predicate, Pageable pageable);

    /**
     * Retrieves audit items for a single organization.
     * @param organizationId the Id corresponding to the organization whose
     *                       AuditItem entities are requested
     * @param predicate      the interface for Boolean typed expressions. Supports
     *                       binding of HTTP parameters to QueryDSL predicate
     * @param pageable       the interface for pagination information
     * @return List of audit items if available, else empty list.
     */
    Page<AuditItem> findAuditItemsForOrganization(long organizationId, Predicate predicate, Pageable pageable);
}
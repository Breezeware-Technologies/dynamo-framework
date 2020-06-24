package net.breezeware.dynamo.audit.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Predicate;

import net.breezeware.dynamo.audit.entity.AuditItem;

/**
 * 
 * Interface to manage audit services.
 * 
 * @author gowtham
 *
 */
public interface AuditService {
	/**
	 * Persists an audit item.
	 * 
	 * @param item audit item.
	 * @return saved entity.
	 */
	AuditItem saveItem(AuditItem item);

	/**
	 * 
	 * Retrieves an audit item by its id.
	 * 
	 * @param id item id.
	 * @return item.
	 */
	AuditItem getItemById(long id);

	/**
	 * Retrieves audit items from all organizations.
	 * 
	 * @return List of audit items if available, else empty list.
	 */
	Page<AuditItem> findAllAuditItems(Predicate predicate, Pageable pageable);

	/**
	 * Retrieves audit items for a single organization.
	 * 
	 * @return List of audit items if available, else empty list.
	 */
	Page<AuditItem> findAuditItemsForOrganization(long organizationId, Predicate predicate, Pageable pageable);
}
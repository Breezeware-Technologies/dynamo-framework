package net.breezeware.dynamo.audit.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.StringPath;

import net.breezeware.dynamo.audit.entity.AuditItem;
import net.breezeware.dynamo.audit.entity.QAuditItem;

@Repository
public interface AuditItemRepository extends JpaRepository<AuditItem, Long>, QuerydslPredicateExecutor<AuditItem>,
		QuerydslBinderCustomizer<QAuditItem> {

	default public void customize(QuerydslBindings bindings, QAuditItem item) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));

		// The customization is to support search a range of dates.
		// Code sample taken from here:
		// https://stackoverflow.com/questions/35155824/can-spring-data-rests-querydsl-integration-be-used-to-perform-more-complex-quer

		bindings.bind(item.auditDateFrom).first((path, value) -> item.auditDate.after(value.toInstant()));

		// add one extra day to the 'auditDateTo' field to include the 'to' date during
		// search.
		bindings.bind(item.auditDateTo).first(
				(path, value) -> item.auditDate.before(new Date(value.getTime() + (1000 * 60 * 60 * 24)).toInstant()));
	}
}
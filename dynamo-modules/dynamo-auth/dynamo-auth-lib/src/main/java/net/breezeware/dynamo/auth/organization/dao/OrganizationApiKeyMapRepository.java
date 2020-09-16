package net.breezeware.dynamo.auth.organization.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.StringPath;

import net.breezeware.dynamo.auth.organization.entity.OrganizationApiKeyMap;
import net.breezeware.dynamo.auth.organization.entity.QOrganizationApiKeyMap;

@Repository
public interface OrganizationApiKeyMapRepository extends JpaRepository<OrganizationApiKeyMap, Long>,
        QuerydslPredicateExecutor<QOrganizationApiKeyMap>, QuerydslBinderCustomizer<QOrganizationApiKeyMap> {

    /**
     * Retrieves an OrganizationApiKey by API key value.
     * @param apiKeyValue the UUID value associated with the API key
     * @return the OrganizationApiKeyMap associated with the API key value
     */
    OrganizationApiKeyMap findByApiKeyValue(UUID apiKeyValue);

    /**
     * Retrieves a list of OrganizationApiKeyMap for an organization.
     * @param organizationId the organization for which the OrganizationApiKeyMap
     *                       are retrieved
     * @return a list of OrganizationApiKeyMap for an organization
     */
    List<OrganizationApiKeyMap> findByOrganizationId(long organizationId);

    default void customize(QuerydslBindings bindings, QOrganizationApiKeyMap feed) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}
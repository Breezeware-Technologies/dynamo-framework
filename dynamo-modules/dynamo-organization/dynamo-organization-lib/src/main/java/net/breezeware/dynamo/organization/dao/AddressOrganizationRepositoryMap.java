package net.breezeware.dynamo.organization.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.types.dsl.StringPath;

import net.breezeware.dynamo.organization.entity.OrganizationAddressMap;
import net.breezeware.dynamo.organization.entity.QOrganizationAddressMap;

public interface AddressOrganizationRepositoryMap extends JpaRepository<OrganizationAddressMap, Long>,
        QuerydslPredicateExecutor<OrganizationAddressMap>, QuerydslBinderCustomizer<QOrganizationAddressMap> {

    @Override
    default void customize(QuerydslBindings bindings, QOrganizationAddressMap address) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}

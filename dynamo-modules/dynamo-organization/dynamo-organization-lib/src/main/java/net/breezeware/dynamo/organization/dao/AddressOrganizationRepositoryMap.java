package net.breezeware.dynamo.organization.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;

import net.breezeware.dynamo.organization.entity.OrganizationAddressMap;
import net.breezeware.dynamo.organization.entity.QOrganizationAddressMap;

public interface AddressOrganizationRepositoryMap extends JpaRepository<OrganizationAddressMap, Long>,
        QuerydslPredicateExecutor<OrganizationAddressMap>, QuerydslBinderCustomizer<QOrganizationAddressMap> {

    Optional<OrganizationAddressMap> findByOrganizationId(long organizationId);

}

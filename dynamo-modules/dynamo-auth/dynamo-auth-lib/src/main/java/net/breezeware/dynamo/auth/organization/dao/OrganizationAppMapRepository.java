package net.breezeware.dynamo.auth.organization.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.dynamo.auth.organization.entity.OrganizationAppMap;

@Repository
public interface OrganizationAppMapRepository extends JpaRepository<OrganizationAppMap, Long> {
    List<OrganizationAppMap> findByOrganizationId(String organizationId);

    OrganizationAppMap findByOrganizationIdAndAppId(String organizationId, String appId);

}
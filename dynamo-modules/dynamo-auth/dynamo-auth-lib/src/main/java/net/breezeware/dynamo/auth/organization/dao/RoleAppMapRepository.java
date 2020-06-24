package net.breezeware.dynamo.auth.organization.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.dynamo.auth.organization.entity.RoleAppMap;

@Repository
public interface RoleAppMapRepository extends JpaRepository<RoleAppMap, Long> {
    List<RoleAppMap> findByRoleId(String roleId);

    RoleAppMap findByRoleIdAndAppId(String roleId, String appId);

    List<RoleAppMap> findByOrganizationIdAndAppId(String organizationId, String appId);

    RoleAppMap findByOrganizationIdAndRoleIdAndAppId(String organizationId, String roleId, String appId);

}
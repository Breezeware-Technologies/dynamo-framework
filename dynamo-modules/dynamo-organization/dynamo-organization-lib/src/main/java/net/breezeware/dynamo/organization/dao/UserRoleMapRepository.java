package net.breezeware.dynamo.organization.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.dynamo.organization.entity.Role;
import net.breezeware.dynamo.organization.entity.UserRoleMap;

@Repository
public interface UserRoleMapRepository extends JpaRepository<UserRoleMap, Long> {

    // UserRoleMap findByRole(Role role);

    List<UserRoleMap> findByRole(Role role);

    Optional<UserRoleMap> findByRoleAndUserId(Role role, long userId);

}

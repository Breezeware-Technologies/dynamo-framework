package net.breezeware.dynamo.organization.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.dynamo.organization.entity.UserGroupMap;

@Repository
public interface UserGroupMapRepository extends JpaRepository<UserGroupMap, Long> {
}

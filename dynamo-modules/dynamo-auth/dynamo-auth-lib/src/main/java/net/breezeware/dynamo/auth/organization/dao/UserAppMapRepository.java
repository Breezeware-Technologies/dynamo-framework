package net.breezeware.dynamo.auth.organization.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.dynamo.auth.organization.entity.UserAppMap;

@Repository
public interface UserAppMapRepository extends JpaRepository<UserAppMap, Long> {
    List<UserAppMap> findByUserId(String userId);
}
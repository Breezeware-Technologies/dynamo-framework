package net.breezeware.dynamo.organization.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.dynamo.organization.entity.UserRegistrationToken;

@Repository
public interface UserRegistrationTokenRepository extends JpaRepository<UserRegistrationToken, Long> {
    UserRegistrationToken findByTokenIgnoreCase(String token);

}
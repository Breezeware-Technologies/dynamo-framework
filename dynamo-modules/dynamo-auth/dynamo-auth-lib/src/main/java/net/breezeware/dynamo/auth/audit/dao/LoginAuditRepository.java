package net.breezeware.dynamo.auth.audit.dao;

import net.breezeware.dynamo.auth.audit.entity.LoginAudit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginAuditRepository extends JpaRepository<LoginAudit, Long> {

}
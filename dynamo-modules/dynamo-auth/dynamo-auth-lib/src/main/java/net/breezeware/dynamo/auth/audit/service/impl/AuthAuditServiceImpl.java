package net.breezeware.dynamo.auth.audit.service.impl;

import net.breezeware.dynamo.auth.audit.dao.LoginAuditRepository;
import net.breezeware.dynamo.auth.audit.entity.LoginAudit;
import net.breezeware.dynamo.auth.audit.service.api.AuthAuditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthAuditServiceImpl implements AuthAuditService {

    @Autowired
    LoginAuditRepository loginAuditRepository;

    
    public LoginAudit auditLogin(LoginAudit loginAudit) {
        return loginAuditRepository.save(loginAudit);
    }
}
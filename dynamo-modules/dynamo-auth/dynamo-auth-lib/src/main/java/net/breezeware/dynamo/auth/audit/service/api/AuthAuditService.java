package net.breezeware.dynamo.auth.audit.service.api;

import net.breezeware.dynamo.auth.audit.entity.LoginAudit;

/**
 * 
 * Interface to manage authentication audit services.
 * 
 * @author gowtham
 *
 */
public interface AuthAuditService {
    LoginAudit auditLogin(LoginAudit loginAudit);
}
package net.breezeware.dynamo.auth.springsecurity;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Interface that extends Spring Security's UserDetailsService. This interface
 * can be implemented by different authentication mechanisms like DB
 * Authentication (i.e., with the help of Dynamo Organization module) or LDAP
 * Authentication (i.e., with the help of Dynamo LDAP module).
 * 
 */
public interface DynamoUserDetailsService extends UserDetailsService {

}
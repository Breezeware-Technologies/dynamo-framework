package net.breezeware.dynamo.ldap.dao;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.dynamo.ldap.entity.User;

@Repository
public interface UserLdapRepository extends LdapRepository<User> {

    User findByUid(String uid);
}
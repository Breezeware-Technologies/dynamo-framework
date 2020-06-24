package net.breezeware.dynamo.ldap.dao;

import java.util.List;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.dynamo.ldap.entity.Group;

@Repository
public interface GroupLdapRepository extends LdapRepository<Group> {

    List<Group> findByMembers(String dn);

    List<Group> findByUniqueMembers(String dn);
}
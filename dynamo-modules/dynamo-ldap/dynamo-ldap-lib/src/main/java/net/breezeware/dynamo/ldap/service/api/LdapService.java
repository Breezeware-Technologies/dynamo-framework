package net.breezeware.dynamo.ldap.service.api;

import java.util.List;

import net.breezeware.dynamo.ldap.entity.Group;
import net.breezeware.dynamo.ldap.entity.User;

public interface LdapService {

    /**
     * Retrieves a single User identified by UID.
     * 
     * @param uid
     *            Unique ID for the user identified by the 'uid' parameter in the
     *            LDAP directory.
     * @return User if found, else null.
     */
    User getUserByUid(String uid);

    /**
     * Retrieves a list of Groups for a single user identified by the DN
     * (Distinguished Name) of the user in LDAP directory.
     * 
     * @param dn
     *            Distinguished Name of the user in LDAP directory.
     * @return List if one or more Groups found, else empty list.
     */
    List<Group> getUserGroups(String dn);

    /**
     * Retrieves all groups available in the LDAP directory.
     * 
     * @return List if one or more Groups found, else empty list.
     */
    List<Group> getGroups();

    /**
     * Retrieves all users available in the LDAP directory.
     * 
     * @return List if one or more Users found, else empty list.
     */
    List<User> getUsers();
}

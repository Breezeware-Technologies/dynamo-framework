package net.breezeware.dynamo.ldap.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.breezeware.dynamo.audit.aspectj.Auditable;
import net.breezeware.dynamo.ldap.dao.GroupLdapRepository;
import net.breezeware.dynamo.ldap.dao.UserLdapRepository;
import net.breezeware.dynamo.ldap.entity.Group;
import net.breezeware.dynamo.ldap.entity.User;
import net.breezeware.dynamo.ldap.service.api.LdapService;

@Service
public class LdapServiceImpl implements LdapService {

    Logger logger = LoggerFactory.getLogger(LdapServiceImpl.class);

    @Autowired
    UserLdapRepository userLdapRepository;

    @Autowired
    GroupLdapRepository groupLdapRepository;

    @Value("${spring.ldap.base}")
    private String springLdapBase;

    @Transactional
    @Auditable(event = "Retrieve User", argNames = "uid")
    public User getUserByUid(String uid) {
        logger.info("Entering getUserByUid(). Uid = {}.", uid);

        User user = userLdapRepository.findByUid(uid);

        logger.info("Leaving getUserByUid(). User = {}.", user);
        return user;
    }

    @Transactional
    @Auditable(event = "Retrive User Groups", argNames = "dn")
    public List<Group> getUserGroups(String dn) {
        logger.info("Entering getUserGroups(). dn = {}.", dn);

        List<Group> groups = new ArrayList<Group>();

        // first get the list of 'unique members' entries from the LDAP directory
        groups = (List<Group>) groupLdapRepository.findByUniqueMembers(dn);

        // if unique members list is empty, check for 'member' entries in LDAP directory
        if (groups != null && groups.size() == 0) {
            groups = (List<Group>) groupLdapRepository.findByMembers(dn);
        }

        logger.info("Leaving getUserGroups(). # of groups = {}", groups.size());
        return groups;
    }

    @Transactional
    @Auditable(event = "Retrieve Groups", argNames = "")
    public List<Group> getGroups() {
        logger.info("Entering getGroups().");

        List<Group> groups = new ArrayList<Group>();

        // first get the list of 'unique members' entries from the LDAP directory
        // LdapQuery query =
        // LdapQueryBuilder.query().base("ou=Groups").where("objectclass").not().is("groupofUniqueNames")
        // .or("objectclass").is("groupOfNames");

        // OrFilter filter = new OrFilter();
        // filter.or(new EqualsFilter("objectclass", "groupOfNames"));
        // LdapQuery query = LdapQueryBuilder.query().base("ou=Groups").filter(filter);

        // groups = (List<Group>) groupLdapRepository.findAll(query);

        groups = (List<Group>) groupLdapRepository.findAll();

        logger.info("Leaving getGroups(). # of groups = {}", groups.size());
        return groups;
    }

    @Transactional
    @Auditable(event = "Retrieve Users", argNames = "")
    public List<User> getUsers() {
        logger.info("Entering getUsers().");

        List<User> users = new ArrayList<User>();

        // first get the list of 'unique members' entries from the LDAP directory
        users = (List<User>) userLdapRepository.findAll();

        logger.info("Leaving getUsers(). # of groups = {}", users.size());
        return users;
    }
}
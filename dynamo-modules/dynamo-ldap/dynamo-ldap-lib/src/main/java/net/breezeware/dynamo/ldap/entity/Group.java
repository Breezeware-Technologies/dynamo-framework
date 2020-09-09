package net.breezeware.dynamo.ldap.entity;

import java.util.Set;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import com.google.gson.Gson;

/**
 * Represents a Group in the LDAP vocabulary. This is equivalent to a Role in
 * the Dynamo Authentication module.
 */
@Entry(objectClasses = { "groupOfNames", "top" })
public class Group {
    @Id
    private Name dn;

    @Attribute(name = "cn")
    private String cn;

    @Attribute(name = "uniqueMember")
    private Set<Name> uniqueMembers;

    @Attribute(name = "member")
    private Set<Name> members;

    public Name getDn() {
        return dn;
    }

    public void setDn(Name dn) {
        this.dn = dn;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public Set<Name> getUniqueMembers() {
        return uniqueMembers;
    }

    public void setUniqueMembers(Set<Name> uniqueMembers) {
        this.uniqueMembers = uniqueMembers;
    }

    public Set<Name> getMembers() {
        return members;
    }

    public void setMembers(Set<Name> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
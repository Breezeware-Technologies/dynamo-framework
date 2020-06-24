package net.breezeware.dynamo.ldap.entity;

import java.util.Arrays;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Attribute.Type;

import com.google.gson.Gson;

import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

/**
 * Represents a User in the LDAP vocabulary.
 * 
 */
@Entry(objectClasses = { "person", "top" })
public class User {
	@Id
	private Name dn;

	@Attribute(name = "cn")
	private String fullName;

	@Attribute(name = "uid")
	private String uid;

	@Attribute(name = "userPassword", type = Type.BINARY)
	private byte[] password;

	public Name getDn() {
		return dn;
	}

	public void setDn(Name dn) {
		this.dn = dn;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
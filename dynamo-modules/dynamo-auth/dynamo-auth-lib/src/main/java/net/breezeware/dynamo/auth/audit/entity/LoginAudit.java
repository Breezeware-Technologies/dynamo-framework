package net.breezeware.dynamo.auth.audit.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

/**
 * 
 * Entity to record the login details.
 * 
 * @author gowtham
 *
 */
@XmlRootElement
@Entity
@Table(name = "audit_login", schema = "dynamo")
public class LoginAudit implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
	@SequenceGenerator(name = "seq_gen", sequenceName = "audit_login_seq", schema = "dynamo", allocationSize = 1)
	private long id;

	@Expose
	@Column(name = "login_date")
	private Calendar loginDate;

	@Expose
	@Column(name = "ip_address")
	private String ipAddress;

	@Expose
	@Column(name = "client_details")
	private String clientDetails;

	@Expose
	@Column(name = "protocol")
	private String protocol;

	@Expose
	@Column(name = "login_email")
	private String loginEmail;

	@Expose
	@Column(name = "login_roles")
	private String loginRoles;

	@Expose
	@Column(name = "created_date")
	private Calendar createdDate;

	@Expose
	@Column(name = "modified_date")
	private Calendar modifiedDate;

	public Calendar getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Calendar loginDate) {
		this.loginDate = loginDate;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getClientDetails() {
		return clientDetails;
	}

	public void setClientDetails(String clientDetails) {
		this.clientDetails = clientDetails;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getLoginEmail() {
		return loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	public String getLoginRoles() {
		return loginRoles;
	}

	public void setLoginRoles(String loginRoles) {
		this.loginRoles = loginRoles;
	}

	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public Calendar getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Calendar modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String toString() {
		return new Gson().toJson(this);
	}
}
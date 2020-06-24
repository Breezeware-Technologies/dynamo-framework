package net.breezeware.dynamo.audit.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.format.annotation.DateTimeFormat;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.querydsl.core.annotations.QueryEntity;

/**
 * Entity to store the audit information. This entity is created for each method
 * that is marked for auditing.
 * 
 */
@XmlRootElement
@Entity
@QueryEntity
@Table(name = "audit_item", schema = "dynamo")
public class AuditItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID attribute.
	 */
	@Expose
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
	@SequenceGenerator(name = "seq_gen", sequenceName = "audit_item_seq", schema = "dynamo", allocationSize = 1)
	private long id;

	/**
	 * Id of the Organization to which this audit item belongs.
	 */
	@Expose
	@Column(name = "organization_id")
	private long organizationId;

	/**
	 * Name of the method being audited.
	 */
	@Expose
	@Column(name = "audit_event")
	private String auditEvent;

	/**
	 * Date when the audit item was created. Could not use Calendar or LocalDate or
	 * LocalDateTime because of issues involving data formatting while submitting
	 * the query for QueryDslBinderCustomizer
	 */
	@Expose
	@Column(name = "audit_date")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Instant auditDate;

	@Expose
	@Transient
	private String auditDateString;

	@Transient
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date auditDateFrom;

	@Transient
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date auditDateTo;

	/**
	 * Unique ID identifying the item being audited. Eg. User ID, Group ID, App ID
	 * etc..
	 */
	@Expose
	@Column(name = "audit_item_id")
	private String auditItemId;

	/**
	 * Category of audit item. Eg., User, Group, App
	 */
	@Expose
	@Column(name = "audit_item_type")
	private String auditItemType;

	/**
	 * Input data provided to the method being audited. It is basically all the
	 * method params and their values as a JSON string.
	 */
	@Expose
	@Column(name = "audit_item_input_data", columnDefinition="clob")
	private String auditItemInputData;

	/**
	 * Output data returned by the method being audited. It is a JSON string.
	 */
	@Expose
	@Column(name = "audit_item_output_data", columnDefinition="clob")
	private String auditItemOutputData;

	/**
	 * Email of the User logged in when the audit item was created
	 */
	@Expose
	@Column(name = "audit_user_email")
	private String auditUserEmail;

	/**
	 * IP address from which the item was accessed
	 */
	@Expose
	@Column(name = "ip_address")
	private String ipAddress;

	/**
	 * Client/browser details from which the item was accessed.
	 */
	@Expose
	@Column(name = "client_details")
	private String clientDetails;

	/**
	 * Client protol using which the item was accessed.
	 */
	@Expose
	@Column(name = "protocol")
	private String protocol;

	/**
	 * Created date
	 */
	@Expose
	@Column(name = "created_date")
	private Calendar createdDate;

	/**
	 * Modified date
	 */
	@Expose
	@Column(name = "modified_date")
	private Calendar modifiedDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}

	public Instant getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Instant auditDate) {
		this.auditDate = auditDate;
	}

	public String getAuditDateString() {
		return auditDateString;
	}

	public void setAuditDateString(String auditDateString) {
		this.auditDateString = auditDateString;
	}

	public Date getAuditDateFrom() {
		return auditDateFrom;
	}

	public void setAuditDateFrom(Date auditDateFrom) {
		this.auditDateFrom = auditDateFrom;
	}

	public Date getAuditDateTo() {
		return auditDateTo;
	}

	public void setAuditDateTo(Date auditDateTo) {
		this.auditDateTo = auditDateTo;
	}

	public String getAuditUserEmail() {
		return auditUserEmail;
	}

	public void setAuditUserEmail(String auditUserEmail) {
		this.auditUserEmail = auditUserEmail;
	}

	public String getAuditItemType() {
		return auditItemType;
	}

	public void setAuditItemType(String auditItemType) {
		this.auditItemType = auditItemType;
	}

	public String getAuditItemId() {
		return auditItemId;
	}

	public void setAuditItemId(String auditItemId) {
		this.auditItemId = auditItemId;
	}

	public String getAuditItemInputData() {
		return auditItemInputData;
	}

	public void setAuditItemInputData(String auditItemInputData) {
		this.auditItemInputData = auditItemInputData;
	}

	public String getAuditItemOutputData() {
		return auditItemOutputData;
	}

	public void setAuditItemOutputData(String auditItemOutputData) {
		this.auditItemOutputData = auditItemOutputData;
	}

	public String getAuditEvent() {
		return auditEvent;
	}

	public void setAuditEvent(String auditEvent) {
		this.auditEvent = auditEvent;
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

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}

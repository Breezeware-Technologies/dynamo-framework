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

import lombok.Getter;
import lombok.Setter;

/**
 * Entity to store the audit information. This entity is created for each method
 * that is marked for auditing.
 */
@XmlRootElement
@Entity
@QueryEntity
@Table(name = "audit_item", schema = "dynamo")
public class AuditItem implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID attribute.
     */
    @Expose
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    @SequenceGenerator(name = "seq_gen", sequenceName = "audit_item_seq", schema = "dynamo", allocationSize = 1)
    @Getter
    @Setter
    private long id;

    /**
     * Id of the Organization to which this audit item belongs.
     */
    @Expose
    @Getter
    @Setter
    @Column(name = "organization_id")
    private long organizationId;

    /**
     * Name of the method being audited.
     */
    @Expose
    @Getter
    @Setter
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
    @Getter
    @Setter
    private Instant auditDate;

    @Expose
    @Transient
    @Getter
    @Setter
    private String auditDateString;

    @Transient
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Getter
    @Setter
    private Date auditDateFrom;

    @Transient
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Getter
    @Setter
    private Date auditDateTo;

    /**
     * Unique ID identifying the item being audited. Eg. User ID, Group ID, App ID
     * etc..
     */
    @Expose
    @Column(name = "audit_item_id")
    @Getter
    @Setter
    private String auditItemId;

    /**
     * Category of audit item. Eg., User, Group, App
     */
    @Expose
    @Column(name = "audit_item_type")
    @Getter
    @Setter
    private String auditItemType;

    /**
     * Input data provided to the method being audited. It is basically all the
     * method params and their values as a JSON string.
     */
    @Expose
    @Column(name = "audit_item_input_data", columnDefinition = "clob")
    @Getter
    @Setter
    private String auditItemInputData;

    /**
     * Output data returned by the method being audited. It is a JSON string.
     */
    @Expose
    @Column(name = "audit_item_output_data", columnDefinition = "clob")
    @Getter
    @Setter
    private String auditItemOutputData;

    /**
     * Email of the User logged in when the audit item was created.
     */
    @Expose
    @Column(name = "audit_user_email")
    @Getter
    @Setter
    private String auditUserEmail;

    /**
     * IP address from which the item was accessed.
     */
    @Expose
    @Column(name = "ip_address")
    @Getter
    @Setter
    private String ipAddress;

    /**
     * Client/browser details from which the item was accessed.
     */
    @Expose
    @Column(name = "client_details")
    @Getter
    @Setter
    private String clientDetails;

    /**
     * Client protol using which the item was accessed.
     */
    @Expose
    @Column(name = "protocol")
    @Getter
    @Setter
    private String protocol;

    /**
     * Created date.
     */
    @Expose
    @Column(name = "created_date")
    @Getter
    @Setter
    private Calendar createdDate;

    /**
     * Modified date.
     */
    @Expose
    @Column(name = "modified_date")
    @Getter
    @Setter
    private Calendar modifiedDate;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

package net.breezeware.dynamo.auth.organization.entity;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Key provided to external entities to access application APIs
 */
@Table(name = "api_key", schema = "dynamo")
@ToString
@Entity
public class ApiKey {

    public static final String KEY_STATUS_ACTIVE = "ACTIVE";
    public static final String KEY_STATUS_INACTIVE = "INACTIVE";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    @SequenceGenerator(name = "seq_gen", sequenceName = "api_key_seq", schema = "dynamo", allocationSize = 1)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    @Column(name = "value")
    private UUID value;

    @Getter
    @Setter
    @Column(name = "status", length = 64)
    private String status;

    @Getter
    @Setter
    @Column(name = "created_date")
    private Instant createdDate;

    @Getter
    @Setter
    @Column(name = "modified_date")
    private Instant modifiedDate;
}
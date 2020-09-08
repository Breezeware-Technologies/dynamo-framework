package net.breezeware.dynamo.auth.organization.entity;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import net.breezeware.dynamo.organization.entity.Organization;

import lombok.Getter;
import lombok.Setter;

/**
 * Entity to map a Dynamo Organization to an API key.
 */
@Entity
@Table(name = "organization_api_key_map", schema = "dynamo")
public class OrganizationApiKeyMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    @SequenceGenerator(name = "seq_gen", sequenceName = "org_api_key_map_seq", schema = "dynamo", allocationSize = 1)
    @Getter
    @Setter
    private long id;

    /**
     * ID of organization being mapped.
     */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    /**
     * ID of the app being mapped.
     */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "api_key_id")
    private ApiKey apiKey;

    @Getter
    @Setter
    @Column(name = "created_date")
    private Instant createdDate;

    @Getter
    @Setter
    @Column(name = "modified_date")
    private Instant modifiedDate;
}
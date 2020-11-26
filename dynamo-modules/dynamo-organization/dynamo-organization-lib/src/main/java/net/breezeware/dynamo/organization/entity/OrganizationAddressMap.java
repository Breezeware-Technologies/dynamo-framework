package net.breezeware.dynamo.organization.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "organization_address_map", schema = "dynamo")
public class OrganizationAddressMap {

    /**
     * Unique key to identify a address,auto-generated value.
     */
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organization_address__map_seq_gen")
    @SequenceGenerator(name = "organization_address__map_seq_gen", sequenceName = "organization_address__map_seq",
            schema = "dynamo", allocationSize = 1)
    private long id;

    @Column(name = "organization_id")
    private long organizationId;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

}

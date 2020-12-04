package net.breezeware.dynamo.organization.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "address", schema = "dynamo")
@Data
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unique key to identify a address,auto-generated value.
     */
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq_gen")
    @SequenceGenerator(name = "address_seq_gen", sequenceName = "address_seq", schema = "dynamo", allocationSize = 1)
    private long id;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    /**
     * ZIP code in an address. This is also called postal code in certain countries.
     */
    @Column(name = "zipcode")
    private String zipcode;
}

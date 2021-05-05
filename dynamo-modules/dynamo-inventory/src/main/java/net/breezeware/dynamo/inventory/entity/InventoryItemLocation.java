package net.breezeware.dynamo.inventory.entity;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "inventory_item_location", schema = "dynamo")
@Entity
public class InventoryItemLocation implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Unique key to identify a inventory item location,auto-generated value.
     */
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_item_location_seq_gen")
    @SequenceGenerator(name = "inventory_item_location_seq_gen", sequenceName = "inventory_item_location_seq",
            schema = "dynamo", allocationSize = 1)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "modified_date")
    private Instant modifiedDate;

}

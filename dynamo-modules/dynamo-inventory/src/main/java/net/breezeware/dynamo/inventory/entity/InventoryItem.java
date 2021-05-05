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
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Table(name = "inventory_item", schema = "dynamo",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "item_sku" }) })
@Entity
public class InventoryItem implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public enum ItemSku {
        BT_BP_v1("BT-BP-v1"), BT_WS_v1("BT-WS-v1"), IG_BG_v1("IG-BG-v1");

        @Getter
        @Setter
        String itemSku;

        ItemSku(String itemSku) {
            this.itemSku = itemSku;
        }

        public static ItemSku getItemSku(String itemSkuName) {
            for (ItemSku itemSku : ItemSku.values()) {
                if (itemSku.getItemSku().equals(itemSkuName)) {
                    return itemSku;
                }
            }
            return null;
        }
    }

    // FIXME: comment for each status
    // Note : update active with patient status
    public enum ItemStatus {
        IN_WAREHOUSE("inwarehouse"), SHIPPED("shipped"), DELIVERED("delivered"), ACTIVE("active"), INACTIVE("inactive"),
        ARCHIVED("archived");

        @Getter
        @Setter
        String itemStatus;

        ItemStatus(String itemStatus) {
            this.itemStatus = itemStatus;
        }
    }

    /**
     * Unique key to identify a inventory item,auto-generated value.
     */
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_item_seq_gen")
    @SequenceGenerator(name = "inventory_item_seq_gen", sequenceName = "inventory_item_seq", schema = "dynamo",
            allocationSize = 1)
    private long id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_category")
    private String itemCategory;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "model_number")
    private String modelNumber;

    @Column(name = "item_sku")
    private String itemSku;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "modified_date")
    private Instant modifiedDate;

}

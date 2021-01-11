package net.breezeware.dynamo.inventory.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.dynamo.inventory.entity.InventoryItem;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    InventoryItem findByItemSku(String itemSku);

}

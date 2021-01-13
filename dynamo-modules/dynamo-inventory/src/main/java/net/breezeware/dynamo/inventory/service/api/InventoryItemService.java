package net.breezeware.dynamo.inventory.service.api;

import java.util.Optional;

import net.breezeware.dynamo.inventory.entity.InventoryItem;

public interface InventoryItemService {

    Optional<InventoryItem> retrieveInventoryItemByItemSku(String itemSku);

    InventoryItem saveInventoryItem(InventoryItem inventoryItem);
}

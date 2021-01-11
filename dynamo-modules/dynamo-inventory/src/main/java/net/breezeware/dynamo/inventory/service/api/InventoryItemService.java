package net.breezeware.dynamo.inventory.service.api;

import net.breezeware.dynamo.inventory.entity.InventoryItem;

public interface InventoryItemService {

    InventoryItem retrieveInventoryItemByItemSku(String itemSku);

    InventoryItem saveInventoryItem(InventoryItem inventoryItem);
}

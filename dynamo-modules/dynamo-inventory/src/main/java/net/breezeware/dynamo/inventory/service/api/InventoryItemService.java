package net.breezeware.dynamo.inventory.service.api;

import java.util.Optional;

import net.breezeware.dynamo.inventory.entity.InventoryItem;
import net.breezeware.dynamo.inventory.entity.InventoryItemLocation;

public interface InventoryItemService {

    Optional<InventoryItem> retrieveInventoryItemByItemSku(String itemSku);

    InventoryItem saveInventoryItem(InventoryItem inventoryItem);

    Optional<InventoryItemLocation> retrieveInventoryItemLocationByName(String locationName);

    InventoryItemLocation saveInventoryItemLocation(InventoryItemLocation inventoryItemLocation);

    void deleteInventoryItem(InventoryItem inventoryItem);
}

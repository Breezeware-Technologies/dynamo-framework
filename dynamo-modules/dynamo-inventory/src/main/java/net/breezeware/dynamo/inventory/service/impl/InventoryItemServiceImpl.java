package net.breezeware.dynamo.inventory.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.breezeware.dynamo.inventory.dao.InventoryItemLocationRepository;
import net.breezeware.dynamo.inventory.dao.InventoryItemRepository;
import net.breezeware.dynamo.inventory.entity.InventoryItem;
import net.breezeware.dynamo.inventory.entity.InventoryItemLocation;
import net.breezeware.dynamo.inventory.service.api.InventoryItemService;

@Service
public class InventoryItemServiceImpl implements InventoryItemService {

    @Autowired
    InventoryItemRepository inventoryItemRepository;

    @Autowired
    InventoryItemLocationRepository inventoryItemLocationRepository;

    @Transactional
    public Optional<InventoryItem> retrieveInventoryItemByItemSku(String itemSku) {
        Optional<InventoryItem> inventoryItem = Optional.ofNullable(inventoryItemRepository.findByItemSku(itemSku));
        return inventoryItem;
    }

    @Transactional
    public InventoryItem saveInventoryItem(InventoryItem inventoryItem) {
        inventoryItem = inventoryItemRepository.save(inventoryItem);
        return inventoryItem;
    }

    @Transactional
    public Optional<InventoryItemLocation> retrieveInventoryItemLocationByName(String locationName) {
        Optional<InventoryItemLocation> inventoryLocation = inventoryItemLocationRepository.findByName(locationName);
        return inventoryLocation;
    }

    @Transactional
    public InventoryItemLocation saveInventoryItemLocation(InventoryItemLocation inventoryItemLocation) {
        inventoryItemLocation = inventoryItemLocationRepository.save(inventoryItemLocation);
        return inventoryItemLocation;
    }

    @Transactional
    public void deleteInventoryItem(InventoryItem inventoryItem) {
        inventoryItemRepository.delete(inventoryItem);
    }

}

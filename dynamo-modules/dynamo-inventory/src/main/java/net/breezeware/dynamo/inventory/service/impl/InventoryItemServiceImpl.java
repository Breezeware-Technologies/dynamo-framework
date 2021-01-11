package net.breezeware.dynamo.inventory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.breezeware.dynamo.inventory.dao.InventoryItemRepository;
import net.breezeware.dynamo.inventory.entity.InventoryItem;
import net.breezeware.dynamo.inventory.service.api.InventoryItemService;

@Service
public class InventoryItemServiceImpl implements InventoryItemService {

    @Autowired
    InventoryItemRepository inventoryItemRepository;

    @Transactional
    public InventoryItem retrieveInventoryItemByItemSku(String itemSku) {
        InventoryItem inventoryItem = inventoryItemRepository.findByItemSku(itemSku);
        return inventoryItem;
    }

    @Transactional
    public InventoryItem saveInventoryItem(InventoryItem inventoryItem) {
        InventoryItem _inventoryItem = inventoryItemRepository.save(inventoryItem);
        return _inventoryItem;
    }

}

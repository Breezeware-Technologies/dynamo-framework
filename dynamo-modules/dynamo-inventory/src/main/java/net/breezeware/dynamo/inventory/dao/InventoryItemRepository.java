package net.breezeware.dynamo.inventory.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import net.breezeware.dynamo.inventory.entity.InventoryItem;
import net.breezeware.dynamo.inventory.entity.QInventoryItem;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long>,
        QuerydslPredicateExecutor<InventoryItem>, QuerydslBinderCustomizer<QInventoryItem> {

    InventoryItem findByItemSku(String itemSku);

    @Override
    default void customize(QuerydslBindings bindings, QInventoryItem root) {
        // TODO Auto-generated method stub

    }

}

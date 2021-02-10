package net.breezeware.dynamo.inventory.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import net.breezeware.dynamo.inventory.entity.InventoryItemLocation;
import net.breezeware.dynamo.inventory.entity.QInventoryItemLocation;

@Repository
public interface InventoryItemLocationRepository extends JpaRepository<InventoryItemLocation, Long>,
        QuerydslPredicateExecutor<InventoryItemLocation>, QuerydslBinderCustomizer<QInventoryItemLocation> {

    Optional<InventoryItemLocation> findByName(String locationName);

    @Override
    default void customize(QuerydslBindings bindings, QInventoryItemLocation root) {
        // TODO Auto-generated method stub

    }
}

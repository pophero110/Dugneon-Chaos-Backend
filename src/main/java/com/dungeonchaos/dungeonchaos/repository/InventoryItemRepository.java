package com.dungeonchaos.dungeonchaos.repository;

import com.dungeonchaos.dungeonchaos.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findByInventory_IdAndItem_Id(Long inventoryId, Long itemId);

    Optional<InventoryItem> findByIdAndInventoryId(Long inventoryItemId, Long inventoryId);
}

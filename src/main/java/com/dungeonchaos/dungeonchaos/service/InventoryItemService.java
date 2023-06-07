package com.dungeonchaos.dungeonchaos.service;

import com.dungeonchaos.dungeonchaos.exception.InformationInvalidException;
import com.dungeonchaos.dungeonchaos.exception.InformationNotFoundException;
import com.dungeonchaos.dungeonchaos.model.Inventory;
import com.dungeonchaos.dungeonchaos.model.InventoryItem;
import com.dungeonchaos.dungeonchaos.model.Item.Equipment;
import com.dungeonchaos.dungeonchaos.model.Item.EquipmentType;
import com.dungeonchaos.dungeonchaos.model.Item.Item;
import com.dungeonchaos.dungeonchaos.model.Item.ItemType;
import com.dungeonchaos.dungeonchaos.model.Player;
import com.dungeonchaos.dungeonchaos.repository.InventoryItemRepository;
import com.dungeonchaos.dungeonchaos.repository.InventoryRepository;
import com.dungeonchaos.dungeonchaos.repository.ItemRepository;
import com.dungeonchaos.dungeonchaos.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryItemService {

    private InventoryRepository inventoryRepository;
    private ItemRepository itemRepository;
    private InventoryItemRepository inventoryItemRepository;
    private PlayerRepository playerRepository;


    @Autowired
    InventoryItemService(InventoryRepository inventoryRepository, ItemRepository itemRepository, InventoryItemRepository inventoryItemRepository, PlayerRepository playerRepository) {
        this.inventoryRepository = inventoryRepository;
        this.itemRepository = itemRepository;
        this.inventoryItemRepository = inventoryItemRepository;
        this.playerRepository = playerRepository;
    }

    public Player equipWeapon(Long inventoryItemId) {
        return equipItem(inventoryItemId, EquipmentType.WEAPON, "weapon");
    }

    public Player equipArmor(Long inventoryItemId) {
        return equipItem(inventoryItemId, EquipmentType.ARMOR, "armor");
    }

    public Player unequipWeapon(Long inventoryItemId) {
        return unequipItem(inventoryItemId, "weapon");
    }

    public Player unequipArmor(Long inventoryItemId) {
        return unequipItem(inventoryItemId, "armor");
    }

    private Player equipItem(Long inventoryItemId, EquipmentType expectedType, String itemType) {
        InventoryItem inventoryItem = getInventoryItem(inventoryItemId);
        validateEquipItem(inventoryItem, expectedType, itemType);
        inventoryItem.setEquipped(true);
        addEquipmentAttributesToPlayerAttributes(inventoryItem);
        return savePlayer(inventoryItem);
    }

    private Player unequipItem(Long inventoryItemId, String itemType) {
        InventoryItem inventoryItem = getInventoryItem(inventoryItemId);
        validateUnequipItem(inventoryItem, itemType);
        inventoryItem.setEquipped(false);
        removeEquipmentAttributesFromPlayerAttributes(inventoryItem);
        return savePlayer(inventoryItem);
    }

    private InventoryItem getInventoryItem(Long inventoryItemId) {
        return inventoryItemRepository.findById(inventoryItemId)
                .orElseThrow(() -> new InformationNotFoundException("InventoryItem is not found with id " + inventoryItemId));
    }

    private void validateEquipItem(InventoryItem inventoryItem, EquipmentType expectedType, String itemType) {
        if (inventoryItem.isEquipped()) {
            throw new InformationInvalidException("The " + itemType + " is already equipped");
        }

        Item item = inventoryItem.getItem();
        if (item.getType().equals(ItemType.POTION)) {
            throw new InformationInvalidException("You cannot equip a Potion as a " + itemType);
        }

        if (!((Equipment) item).getEquipmentType().equals(expectedType)) {
            throw new InformationInvalidException("You cannot equip " + item.getName() + " as a " + itemType);
        }

        Inventory inventory = inventoryItem.getInventory();
        boolean isItemAlreadyEquipped = inventory.getInventoryItems().stream()
                .anyMatch(invItem -> invItem.isEquipped() && invItem.getItem() instanceof Equipment &&
                        ((Equipment) invItem.getItem()).getEquipmentType().equals(expectedType));

        if (isItemAlreadyEquipped) {
            throw new InformationInvalidException("You can only equip one " + itemType + " at a time");
        }
    }

    private void validateUnequipItem(InventoryItem inventoryItem, String itemType) {
        if (!inventoryItem.isEquipped()) {
            throw new InformationInvalidException("You haven't equipped an " + itemType + " yet");
        }
    }

    private void addEquipmentAttributesToPlayerAttributes(InventoryItem inventoryItem) {
        Equipment equipment = (Equipment) inventoryItem.getItem();
        Player player = inventoryItem.getInventory().getPlayer();
        player.setAttack(player.getAttack() + equipment.getAttack());
        player.setHealth(player.getHealth() + equipment.getHealth());
        player.setDefense(player.getDefense() + equipment.getDefense());
        player.setSpeed(player.getSpeed() + equipment.getSpeed());
        playerRepository.save(player);
    }

    private void removeEquipmentAttributesFromPlayerAttributes(InventoryItem inventoryItem) {
        Equipment equipment = (Equipment) inventoryItem.getItem();
        Player player = inventoryItem.getInventory().getPlayer();
        player.setAttack(player.getAttack() - equipment.getAttack());
        player.setHealth(player.getHealth() - equipment.getHealth());
        player.setDefense(player.getDefense() - equipment.getDefense());
        player.setSpeed(player.getSpeed() - equipment.getSpeed());
        if (player.getHealth() <= 0) {
            throw new InformationInvalidException("You will die if you unequip this equipment");
        }
        playerRepository.save(player);
    }

    private Player savePlayer(InventoryItem inventoryItem) {
        return inventoryItemRepository.save(inventoryItem).getInventory().getPlayer();
    }
}

package com.dungeonchaos.dungeonchaos.service;

import com.dungeonchaos.dungeonchaos.exception.InformationInvalidException;
import com.dungeonchaos.dungeonchaos.exception.InformationNotFoundException;
import com.dungeonchaos.dungeonchaos.model.Inventory;
import com.dungeonchaos.dungeonchaos.model.InventoryItem;
import com.dungeonchaos.dungeonchaos.model.Item.*;
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
    private InventoryService inventoryService;


    @Autowired
    InventoryItemService(InventoryRepository inventoryRepository,
                         ItemRepository itemRepository,
                         InventoryItemRepository inventoryItemRepository,
                         PlayerRepository playerRepository,
                         InventoryService inventoryService) {
        this.inventoryRepository = inventoryRepository;
        this.itemRepository = itemRepository;
        this.inventoryItemRepository = inventoryItemRepository;
        this.playerRepository = playerRepository;
        this.inventoryService = inventoryService;
    }

    /**
     * Equips a weapon for the player.
     * @param inventoryItemId The ID of the inventory item representing the weapon.
     * @return The updated player.
     */
    public Player equipWeapon(Long inventoryItemId) {
        return equipItem(inventoryItemId, EquipmentType.WEAPON, "weapon");
    }

    /**
     * Equips an armor for the player.
     * @param inventoryItemId The ID of the inventory item representing the armor.
     * @return The updated player.
     */
    public Player equipArmor(Long inventoryItemId) {
        return equipItem(inventoryItemId, EquipmentType.ARMOR, "armor");
    }

    /**
     * Unequips the currently equipped weapon.
     * @param inventoryItemId The ID of the inventory item representing the weapon.
     * @return The updated player.
     */
    public Player unequipWeapon(Long inventoryItemId) {
        return unequipItem(inventoryItemId, "weapon");
    }

    /**
     * Unequips the currently equipped armor.
     * @param inventoryItemId The ID of the inventory item representing the armor.
     * @return The updated player.
     */
    public Player unequipArmor(Long inventoryItemId) {
        return unequipItem(inventoryItemId, "armor");
    }

    /**
     * Consumes a potion from the player's inventory.
     * @param inventoryItemId The ID of the inventory item representing the potion.
     * @return The updated player.
     * @throws InformationInvalidException if the item is not a potion.
     */
    public Player consumePotion(Long inventoryItemId) {
        InventoryItem inventoryItem = getInventoryItem(inventoryItemId);
        Item item = inventoryItem.getItem();
        Inventory inventory = inventoryItem.getInventory();
        Player player = inventoryItem.getInventory().getPlayer();
        if (!item.getType().equals(ItemType.POTION)) {
            throw new InformationInvalidException("You can not drink it! " + item.getName() + " is not a potion");
        }
        if (((Potion) item).getPotionType().equals(PotionType.HEALTH)) {
            int effectValue = getPotionEffectNumber(((Potion) item).getPotionEffect());
            player.setHealth(player.getHealth() + effectValue);
            inventoryItem.decreaseItemQuantityByOne();
            if (inventoryItem.getItemQuantity() <= 0) {
                inventory.removeInventoryItem(inventoryItem);
                inventoryItemRepository.delete(inventoryItem);
            }
        }
        return inventoryRepository.save(inventory).getPlayer();
    }

    /**
     * Gets the numeric effect value from a potion effect string.
     * @param potionEffect The potion effect string.
     * @return The numeric effect value.
     */
    private int getPotionEffectNumber(String potionEffect) {
        String numberString = potionEffect.replaceAll("\\D+", "");
        int number = Integer.parseInt(numberString);
        return number;
    }

    /**
     * Equips an item for the player.
     * @param inventoryItemId The ID of the inventory item representing the item to equip.
     * @param expectedType The expected equipment type.
     * @param itemType The type of item being equipped.
     * @return The updated player.
     */
    private Player equipItem(Long inventoryItemId, EquipmentType expectedType, String itemType) {
        InventoryItem inventoryItem = getInventoryItem(inventoryItemId);
        validateEquipItem(inventoryItem, expectedType, itemType);
        inventoryItem.setEquipped(true);
        addEquipmentAttributesToPlayerAttributes(inventoryItem);
        return savePlayer(inventoryItem);
    }

    /**
     * Unequips an item for the player.
     * @param inventoryItemId The ID of the inventory item representing the item to unequip.
     * @param itemType The type of item being unequipped.
     * @return The updated player.
     */
    private Player unequipItem(Long inventoryItemId, String itemType) {
        InventoryItem inventoryItem = getInventoryItem(inventoryItemId);
        validateUnequipItem(inventoryItem, itemType);
        inventoryItem.setEquipped(false);
        removeEquipmentAttributesFromPlayerAttributes(inventoryItem);
        return savePlayer(inventoryItem);
    }

    /**
     * Retrieves the inventory item with the given ID.
     * @param inventoryItemId The ID of the inventory item.
     * @return The inventory item.
     * @throws InformationNotFoundException if the inventory item is not found with the given ID.
     */
    private InventoryItem getInventoryItem(Long inventoryItemId) {
        return inventoryItemRepository.findById(inventoryItemId)
                .orElseThrow(() -> new InformationNotFoundException("InventoryItem is not found with id " + inventoryItemId));
    }

    /**
     * Validates the item before equipping it.
     * @param inventoryItem The inventory item to validate.
     * @param expectedType The expected equipment type.
     * @param itemType The type of item being equipped.
     * @throws InformationInvalidException if the item is already equipped, is not an equipment, or is of the wrong type.
     */
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

    /**
     * Validates the item before unequipping it.
     * @param inventoryItem The inventory item to validate.
     * @param itemType The type of item being unequipped.
     * @throws InformationInvalidException if the item is not equipped.
     */
    private void validateUnequipItem(InventoryItem inventoryItem, String itemType) {
        if (!inventoryItem.isEquipped()) {
            throw new InformationInvalidException("You haven't equipped an " + itemType + " yet");
        }
    }

    /**
     * Adds the attributes of the equipped item to the player's attributes.
     * @param inventoryItem The equipped inventory item.
     */
    private void addEquipmentAttributesToPlayerAttributes(InventoryItem inventoryItem) {
        Equipment equipment = (Equipment) inventoryItem.getItem();
        Player player = inventoryItem.getInventory().getPlayer();
        player.setAttack(player.getAttack() + equipment.getAttack());
        player.setHealth(player.getHealth() + equipment.getHealth());
        player.setDefense(player.getDefense() + equipment.getDefense());
        player.setSpeed(player.getSpeed() + equipment.getSpeed());
        playerRepository.save(player);
    }

    /**
     * Removes the attributes of the unequipped item from the player's attributes.
     * @param inventoryItem The unequipped inventory item.
     * @throws InformationInvalidException if unequipping the item results in the player's health being <= 0.
     */
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

    /**
     * Saves the inventory item and returns the player.
     * @param inventoryItem The inventory item to save.
     * @return The updated player.
     */
    private Player savePlayer(InventoryItem inventoryItem) {
        return inventoryItemRepository.save(inventoryItem).getInventory().getPlayer();
    }
}

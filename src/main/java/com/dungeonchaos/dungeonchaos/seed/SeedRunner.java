package com.dungeonchaos.dungeonchaos.seed;

import com.dungeonchaos.dungeonchaos.model.*;
import com.dungeonchaos.dungeonchaos.model.Character;
import com.dungeonchaos.dungeonchaos.model.Item.*;
import com.dungeonchaos.dungeonchaos.repository.*;
import com.dungeonchaos.dungeonchaos.service.FightService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class SeedRunner implements ApplicationRunner {
    private final CharacterRepository characterRepository;

    private final BoardRepository boardRepository;

    private final MonsterRepository monsterRepository;
    private final PlayerRepository playerRepository;
    private final ItemRepository itemRepository;
    private final InventoryRepository inventoryRepository;

    private final FightService fightService;
    private final InventoryItemRepository inventoryItemRepository;


    @Autowired
    SeedRunner(CharacterRepository characterRepository, BoardRepository boardRepository, MonsterRepository monsterRepository, PlayerRepository playerRepository, FightService fightService, ItemRepository itemRepository, InventoryRepository inventoryRepository, InventoryItemRepository inventoryItemRepository) {
        this.characterRepository = characterRepository;
        this.boardRepository = boardRepository;
        this.monsterRepository = monsterRepository;
        this.playerRepository = playerRepository;
        this.inventoryRepository = inventoryRepository;
        this.itemRepository = itemRepository;
        this.fightService = fightService;
        this.inventoryItemRepository = inventoryItemRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Player> players = playerRepository.findAll();
        if (players.isEmpty()) {
            createCharactersAndPlayer();
            createBoard();
            createMonsters();
            createFight();
            createItemsAndInventory();
            createInventory();
        }
    }

    private void createBoard() {
        ObjectMapper objectMapper = new ObjectMapper();
        char[][] boardMatrix = {
                {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'W', 'P', 'W'},
                {'W', 'W', 'P', 'W', 'W', 'W', 'W', 'W', 'P', 'W', 'P', 'W'},
                {'P', 'P', 'P', 'W', 'P', 'P', 'S', 'W', 'P', 'W', 'P', 'P'},
                {'P', 'W', 'P', 'W', 'P', 'W', 'W', 'W', 'W', 'W', 'W', 'W'},
                {'P', 'W', 'P', 'W', 'P', 'W', 'P', 'P', 'P', 'P', 'P', 'W'},
                {'P', 'W', 'P', 'W', 'P', 'W', 'P', 'W', 'W', 'W', 'P', 'W'},
                {'P', 'W', 'P', 'W', 'P', 'P', 'P', 'W', 'P', 'W', 'P', 'W'},
                {'P', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'P', 'W', 'P', 'W'},
                {'P', 'W', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'W'},
                {'P', 'W', 'P', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W'},
                {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                {'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'E', 'W'}
        };

        try {
            String jsonData = objectMapper.writeValueAsString(boardMatrix);
            Board board = new Board(jsonData);
            boardRepository.save(board);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    private void createCharactersAndPlayer() {
        Character warrior = new Character("Warrior", 50, 10, 10, 20);
        Character rogue = new Character("Rogue", 40, 15, 5, 40);
        Player playerWarrior = new Player(warrior);
        playerWarrior.setAttack(100);
        playerWarrior.setSpeed(100);
        playerWarrior.setDifficulty(5);
        playerWarrior.setIdentityKey("1945538");
        playerRepository.save(playerWarrior);
        characterRepository.save(warrior);
        characterRepository.save(rogue);
    }

    private void createMonsters() {
        Monster goblin = new Monster("Goblin", 20, 15, 0, 20, 1);
        Monster badGnome = new Monster("Bad Gnome", 20, 20, 0, 30, 2);
        Monster zombie = new Monster("Undead", 30, 20, 2, 5, 3);
        Monster troll = new Monster("Troll", 40, 30, 10, 10, 4);
        Monster dragon = new Monster("Dragon", 50, 35, 10, 30, 5);
        monsterRepository.save(goblin);
        monsterRepository.save(badGnome);
        monsterRepository.save(zombie);
        monsterRepository.save(troll);
        monsterRepository.save(dragon);
    }

    private void createFight() {
        fightService.startFight(1L);
    }

    private void createItemsAndInventory() {
        Optional<Player> player = playerRepository.findById(1L);
        if (player.isPresent()) {
            Inventory inventory = new Inventory();
            inventory.setPlayer(player.get());
            inventoryRepository.save(inventory);

            Item item1 = new Equipment();
            item1.setName("Plain Sword");
            item1.setType(ItemType.EQUIPMENT);
            item1.setRarity(Rarity.COMMON);
            ((Equipment) item1).setAttack(5);
            ((Equipment) item1).setEquipmentType(EquipmentType.WEAPON);

            Item item2 = new Equipment();
            item2.setName("Round Shield");
            item2.setType(ItemType.EQUIPMENT);
            item2.setRarity(Rarity.COMMON);
            ((Equipment) item2).setDefense(5);
            ((Equipment) item2).setEquipmentType(EquipmentType.ARMOR);

            Item item3 = new Potion();
            setItemToPotion(item3);

            Item item4 = new Equipment();
            item4.setName("Broad Sword");
            item4.setType(ItemType.EQUIPMENT);
            item4.setRarity(Rarity.UNCOMMON);
            ((Equipment) item4).setAttack(10);
            ((Equipment) item4).setEquipmentType(EquipmentType.WEAPON);

            Item item5 = new Equipment();
            item5.setName("Viking Shield");
            item5.setType(ItemType.EQUIPMENT);
            item5.setRarity(Rarity.UNCOMMON);
            ((Equipment) item5).setDefense(10);
            ((Equipment) item5).setEquipmentType(EquipmentType.ARMOR);

            Item item6 = new Equipment();
            item6.setName("Plain Dagger");
            item6.setType(ItemType.EQUIPMENT);
            item6.setRarity(Rarity.COMMON);
            ((Equipment) item6).setAttack(5);
            ((Equipment) item6).setEquipmentType(EquipmentType.WEAPON);

            Item item7 = new Equipment();
            item7.setName("Bowie Knife");
            item7.setType(ItemType.EQUIPMENT);
            item7.setRarity(Rarity.UNCOMMON);
            ((Equipment) item7).setAttack(10);
            ((Equipment) item7).setEquipmentType(EquipmentType.WEAPON);

            Item item8 = new Equipment();
            item8.setName("Relic Blade Knife");
            item8.setType(ItemType.EQUIPMENT);
            item8.setRarity(Rarity.RARE);
            ((Equipment) item8).setAttack(15);
            ((Equipment) item8).setEquipmentType(EquipmentType.WEAPON);

            Item item9 = new Equipment();
            item9.setName("Cross Shield");
            item9.setType(ItemType.EQUIPMENT);
            item9.setRarity(Rarity.RARE);
            ((Equipment) item9).setDefense(15);
            ((Equipment) item9).setEquipmentType(EquipmentType.ARMOR);

            itemRepository.save(item1);
            itemRepository.save(item2);
            itemRepository.save(item3);
            itemRepository.save(item4);
            itemRepository.save(item5);
            itemRepository.save(item6);
            itemRepository.save(item7);
            itemRepository.save(item8);
            itemRepository.save(item9);

            InventoryItem inventoryItem = new InventoryItem();
            inventoryItem.setInventory(inventory);
            inventoryItem.setItem(item1);

            InventoryItem inventoryItem2 = new InventoryItem();
            inventoryItem2.setInventory(inventory);
            inventoryItem2.setItem(item2);
            inventoryItem2.setItemQuantity(2);

            InventoryItem inventoryItem3 = new InventoryItem();
            inventoryItem3.setInventory(inventory);
            inventoryItem3.setItem(item3);
            inventoryItem3.setItemQuantity(2);

            InventoryItem inventoryItem4 = new InventoryItem();
            inventoryItem4.setInventory(inventory);
            inventoryItem4.setItem(item4);

            InventoryItem inventoryItem5 = new InventoryItem();
            inventoryItem5.setInventory(inventory);
            inventoryItem5.setItem(item5);

            InventoryItem inventoryItem6 = new InventoryItem();
            inventoryItem6.setInventory(inventory);
            inventoryItem6.setItem(item6);

            InventoryItem inventoryItem7 = new InventoryItem();
            inventoryItem7.setInventory(inventory);
            inventoryItem7.setItem(item7);

            InventoryItem inventoryItem8 = new InventoryItem();
            inventoryItem8.setInventory(inventory);
            inventoryItem8.setItem(item8);

            InventoryItem inventoryItem9 = new InventoryItem();
            inventoryItem9.setInventory(inventory);
            inventoryItem9.setItem(item9);


            Set<InventoryItem> inventoryItems = new HashSet<>();
            inventoryItems.add(inventoryItem);
            inventoryItems.add(inventoryItem2);
            inventoryItems.add(inventoryItem3);
            inventoryItems.add(inventoryItem4);
            inventoryItems.add(inventoryItem5);
            inventoryItems.add(inventoryItem6);
            inventoryItems.add(inventoryItem7);
            inventoryItems.add(inventoryItem8);
            inventoryItems.add(inventoryItem9);

            inventory.setInventoryItems(inventoryItems);

            inventoryRepository.save(inventory);

        }
    }

    private void createInventory() {
        Inventory emtpyInventory = new Inventory();
        inventoryRepository.save(emtpyInventory);

        Inventory inventoryWithOneItem = new Inventory();
        inventoryRepository.save(inventoryWithOneItem);

        InventoryItem inventoryItem = new InventoryItem();
        Optional<Item> item3 = itemRepository.findById(3L);
        if (item3.isPresent()) {
            inventoryItem.setItem(item3.get());
            inventoryItem.setInventory(inventoryWithOneItem);
            inventoryItem.setItemQuantity(2);
            inventoryItemRepository.save(inventoryItem);
        }

        inventoryWithOneItem.getInventoryItems().add(inventoryItem);
        inventoryRepository.save(inventoryWithOneItem);
    }

    private void setItemToPotion(Item item) {
        item.setName("Health Potion");
        item.setType(ItemType.POTION);
        item.setRarity(Rarity.COMMON);
        ((Potion) item).setPotionType(PotionType.HEALTH);
        ((Potion) item).setPotionEffect("Restores 20 health points");
        ((Potion) item).setPotionDuration(0);
    }
}

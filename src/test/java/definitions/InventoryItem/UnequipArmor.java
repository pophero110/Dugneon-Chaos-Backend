package definitions.InventoryItem;

import com.dungeonchaos.dungeonchaos.DungeonChaosApplication;
import com.dungeonchaos.dungeonchaos.model.Character;
import com.dungeonchaos.dungeonchaos.model.Inventory;
import com.dungeonchaos.dungeonchaos.model.InventoryItem;
import com.dungeonchaos.dungeonchaos.model.Item.*;
import com.dungeonchaos.dungeonchaos.model.Player;
import com.dungeonchaos.dungeonchaos.repository.InventoryItemRepository;
import com.dungeonchaos.dungeonchaos.repository.InventoryRepository;
import com.dungeonchaos.dungeonchaos.repository.ItemRepository;
import com.dungeonchaos.dungeonchaos.repository.PlayerRepository;
import definitions.board.GenerateBoard;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DungeonChaosApplication.class)
public class UnequipArmor {
    private final Logger log = LoggerFactory.getLogger(GenerateBoard.class);
    private static final String BASE_URL = "http://localhost:";

    private static ResponseEntity response;

    @LocalServerPort
    String port;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Autowired
    private ItemRepository itemRepository;
    private InventoryItem inventoryItem;
    private Player player;

    private final int amountOfAttackIncreased = 5;
    @Given("the inventory item is available and it's a equipped armor")
    public void theInventoryItemIsAvailableAndItSAEquippedArmor() {
        Character warrior = new Character("Warrior", 90, 10, 20, 30);
        Player player = new Player(warrior);
        player.setAttack(player.getAttack() + amountOfAttackIncreased);
        player.setDefense(player.getDefense() + amountOfAttackIncreased);
        player.setHealth(player.getHealth() + amountOfAttackIncreased);
        player.setSpeed(player.getSpeed() + amountOfAttackIncreased);
        this.player = playerRepository.save(player);
        Inventory inventory = new Inventory();
        inventory.setPlayer(player);
        inventoryRepository.save(inventory);

        Item item1 = new Equipment();
        item1.setName("Sword");
        item1.setType(ItemType.EQUIPMENT);
        item1.setRarity(Rarity.COMMON);
        ((Equipment) item1).setAttack(amountOfAttackIncreased);
        ((Equipment) item1).setDefense(amountOfAttackIncreased);
        ((Equipment) item1).setSpeed(amountOfAttackIncreased);
        ((Equipment) item1).setHealth(amountOfAttackIncreased);
        ((Equipment) item1).setEquipmentType(EquipmentType.ARMOR);

        itemRepository.save(item1);

        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setInventory(inventory);
        inventoryItem.setItem(item1);
        inventoryItem.setEquipped(true);

        inventory.getInventoryItems().add(inventoryItem);
        this.inventoryItem = inventoryItemRepository.save(inventoryItem);
    }

    @When("the player unequips the armor")
    public void thePlayerUnequipsTheArmor() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            response = new RestTemplate().exchange(BASE_URL + port + "/api/inventoryItems/" + inventoryItem.getId() + "/unequipArmor", HttpMethod.PUT, null, String.class);
            Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }

    @Then("the player's attributes should be decreased with the armor attributes")
    public void thePlayerSAttributesShouldBeDecreasedWithTheArmorAttributes() {
        Optional<Player> player = playerRepository.findById(this.player.getId());
        Assertions.assertEquals(this.player.getAttack() - amountOfAttackIncreased,player.get().getAttack());
        Assertions.assertEquals(this.player.getDefense() - amountOfAttackIncreased,player.get().getDefense());
        Assertions.assertEquals(this.player.getHealth() - amountOfAttackIncreased,player.get().getHealth());
        Assertions.assertEquals(this.player.getSpeed() - amountOfAttackIncreased,player.get().getSpeed());
    }

    @And("the armor is marked as unequipped")
    public void theArmorIsMarkedAsUnequipped() {
        Optional<InventoryItem> inventoryItem = inventoryItemRepository.findById(this.inventoryItem.getId());
        Assertions.assertEquals( false, inventoryItem.get().isEquipped());
    }
}

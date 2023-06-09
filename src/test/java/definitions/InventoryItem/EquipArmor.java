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
public class EquipArmor {
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
    @Given("the inventory item is available and it's a unequipped armor")
    public void theInventoryItemIsAvailableAndItSAUnequippedArmor() {
        Character warrior = new Character("Warrior", 90, 10, 20, 30);
        Player player = new Player(warrior);
        this.player = playerRepository.save(player);
        Inventory inventory = new Inventory();
        inventory.setPlayer(player);
        inventoryRepository.save(inventory);

        Item item1 = new Equipment();
        item1.setName("Shield");
        item1.setType(ItemType.EQUIPMENT);
        item1.setRarity(Rarity.COMMON);
        ((Equipment) item1).setDefense(5);
        ((Equipment) item1).setEquipmentType(EquipmentType.ARMOR);

        itemRepository.save(item1);

        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setInventory(inventory);
        inventoryItem.setItem(item1);

        inventory.getInventoryItems().add(inventoryItem);
        this.inventoryItem = inventoryItemRepository.save(inventoryItem);
    }

    @When("the player equips the armor")
    public void thePlayerEquipsTheArmor() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            response = new RestTemplate().exchange(BASE_URL + port + "/api/inventoryItems/" + inventoryItem.getId() + "/equipArmor", HttpMethod.PUT, null, String.class);
            Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }

    @Then("the player's attributes should be updated with the armor attributes")
    public void thePlayerSAttributesShouldBeUpdatedWithTheArmorSAttributes() {
        Optional<Player> player = playerRepository.findById(this.player.getId());
        Item item = inventoryItem.getItem();
        Assertions.assertEquals(this.player.getDefense() + ((Equipment) item).getDefense(),player.get().getDefense());
    }

    @And("the armor is marked as equipped")
    public void theArmorIsMarkedAsEquipped() {
        Optional<InventoryItem> inventoryItem = inventoryItemRepository.findById(this.inventoryItem.getId());
        Assertions.assertEquals( true, inventoryItem.get().isEquipped());
    }
}

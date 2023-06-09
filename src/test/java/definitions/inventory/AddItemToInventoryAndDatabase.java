package definitions.inventory;

import com.dungeonchaos.dungeonchaos.DungeonChaosApplication;
import com.dungeonchaos.dungeonchaos.model.Inventory;
import com.dungeonchaos.dungeonchaos.model.InventoryItem;
import com.dungeonchaos.dungeonchaos.repository.InventoryItemRepository;
import com.dungeonchaos.dungeonchaos.request.InventoryRequest;
import com.dungeonchaos.dungeonchaos.service.InventoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import definitions.board.GenerateBoard;
import io.cucumber.java.en.And;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Optional;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DungeonChaosApplication.class)
public class AddItemToInventoryAndDatabase {

    private final Logger log = LoggerFactory.getLogger(GenerateBoard.class);
    private static final String BASE_URL = "http://localhost:";

    private static ResponseEntity response;

    @LocalServerPort
    String port;

    private Inventory inventory;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    private Long itemId;

    @Given("an inventory of ID {int} without item")
    public void anInventoryOfIDWithoutItem(int inventoryId) {
        Inventory inventory = inventoryService.getInventoryById(Long.valueOf(inventoryId));
        this.inventory = inventory;
        Assertions.assertNotNull(inventory);
        int expectedInventoryItemCount = 0;
        Assertions.assertEquals(expectedInventoryItemCount, inventory.getInventoryItems().size());
    }

    @When("I sends a POST request to {string} with the item ID of {int}")
    public void iSendsAPOSTRequestToWithTheItemIDOf(String endpoint, int itemId) {
        try {
            this.itemId = Long.valueOf(itemId);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            // Create the request body
            InventoryRequest inventoryRequest = new InventoryRequest(this.itemId);

            HttpEntity<InventoryRequest> requestEntity = new HttpEntity<>(inventoryRequest, headers);


            response = new RestTemplate().exchange(BASE_URL + port + endpoint, HttpMethod.POST, requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }

    @Then("the response body should contain the added item")
    public void theResponseBodyShouldContainTheAddedItem() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Inventory inventory = objectMapper.readValue((String) response.getBody(), Inventory.class);

            // Perform assertions on the inventory details
            Assert.assertNotNull(inventory);
            Optional<InventoryItem> item = inventory.getInventoryItems().stream().filter(inventoryItem -> inventoryItem.getItem().getId() == this.itemId).findFirst();
            Assert.assertTrue(item.isPresent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @And("the inventoryItem should be created in database with quantity of {int}")
    public void theInventoryItemShouldBeCreatedInDatabaseWithQuantityOf(int expectedQuantity) {
        Optional<InventoryItem> inventoryItem = inventoryItemRepository.findByInventory_IdAndItem_Id(inventory.getId(), itemId);
        Assert.assertTrue(inventoryItem.isPresent());
        Assert.assertEquals(expectedQuantity, inventoryItem.get().getItemQuantity());
    }
}

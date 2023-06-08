package definitions.inventory;

import com.dungeonchaos.dungeonchaos.DungeonChaosApplication;
import com.dungeonchaos.dungeonchaos.model.Inventory;
import com.dungeonchaos.dungeonchaos.model.InventoryItem;
import com.dungeonchaos.dungeonchaos.model.Item.Item;
import com.dungeonchaos.dungeonchaos.repository.InventoryItemRepository;
import com.dungeonchaos.dungeonchaos.request.InventoryRequest;
import com.dungeonchaos.dungeonchaos.service.InventoryService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import definitions.board.GetBoard;
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
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Optional;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DungeonChaosApplication.class)
public class RemoveItemFromInventoryAndDatabase {
    private final Logger log = LoggerFactory.getLogger(GetBoard.class);
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

    @Given("an inventory of ID {int} with the item is available")
    public void anInventoryOfIDWithTheItemIsAvailable(int inventoryId) {
        Inventory inventory = inventoryService.getInventoryById(Long.valueOf(inventoryId));
        this.inventory = inventory;
        Assertions.assertNotNull(inventory);
    }

    @When("I sends a PUT request to {string} with the the item ID {int}")
    public void iADELETERequestToWithTheTheItemID(String endpoint, int itemId) {
        try {
            this.itemId = Long.valueOf(itemId);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            // Create the request body
            InventoryRequest inventoryRequest = new InventoryRequest(this.itemId);

            HttpEntity<InventoryRequest> requestEntity = new HttpEntity<>(inventoryRequest, headers);

            response = new RestTemplate().exchange(BASE_URL + port + endpoint, HttpMethod.PUT, requestEntity, String.class);
            Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }

    @Then("the response body should not container the item")
    public void theResponseBodyShouldNotContainerTheItem() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Inventory inventory = objectMapper.readValue((String) response.getBody(), Inventory.class);
            this.inventory = inventory;


            Assert.assertNotNull(inventory);
            Optional<InventoryItem> item = inventory.getInventoryItems().stream().filter(inventoryItem -> inventoryItem.getItem().getId() == this.itemId).findFirst();
            Assert.assertTrue(item.isEmpty());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @And("the item should be deleted in database")
    public void theItemShouldBeDeletedInDatabase() {
        Optional<InventoryItem> inventoryItem = inventoryItemRepository.findByInventory_IdAndItem_Id(inventory.getId(), itemId);
        Assert.assertTrue(inventoryItem.isEmpty());
    }
}

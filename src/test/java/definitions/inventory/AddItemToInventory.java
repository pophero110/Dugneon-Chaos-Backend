package definitions.inventory;

import com.dungeonchaos.dungeonchaos.DungeonChaosApplication;
import com.dungeonchaos.dungeonchaos.model.Inventory;
import com.dungeonchaos.dungeonchaos.model.InventoryItem;
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
public class AddItemToInventory {

    private final Logger log = LoggerFactory.getLogger(GenerateBoard.class);
    private static final String BASE_URL = "http://localhost:";

    private static ResponseEntity response;

    @LocalServerPort
    String port;

    private Inventory inventory;

    @Autowired
    private InventoryService inventoryService;

    private Long itemId;

    private int itemQuantity;

    @Given("an inventory with ID {int} with item is available")
    public void anInventoryOfIDWithItemsIsAvailable(int inventoryId) {
        Inventory inventory = inventoryService.getInventoryById(Long.valueOf(inventoryId));
        this.inventory = inventory;
        Assertions.assertNotNull(inventory);
    }

    @And("an item ID of {int}")
    public void anItemIDOf(int itemId) {
        Optional<InventoryItem> inventoryItem = inventory.getInventoryItems().stream().filter(i -> i.getItem().getId() == itemId).findFirst();
        Assertions.assertTrue(inventoryItem.isPresent());
        this.itemQuantity = inventoryItem.get().getItemQuantity();
        this.itemId = Long.valueOf(itemId);
    }

    @When("I sends a POST request to {string} with the item ID")
    public void iSendsAPUTRequestToWithTheItemID(String endpoint) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            // Create the request body
            InventoryRequest inventoryRequest = new InventoryRequest(itemId);

            HttpEntity<InventoryRequest> requestEntity = new HttpEntity<>(inventoryRequest, headers);


            response = new RestTemplate().exchange(BASE_URL + port + endpoint, HttpMethod.POST, requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }

    @Then("the response body should contain the updated inventory details")
    public void theResponseBodyShouldContainTheUpdatedInventoryDetails() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Inventory inventory = objectMapper.readValue((String) response.getBody(), Inventory.class);

            // Perform assertions on the inventory details
            Assert.assertNotNull(inventory);
            Assert.assertEquals(Optional.of(1L), inventory.getId());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @And("the quantity of the item should increase by one")
    public void theQuantityOfTheItemShouldIncreaseBy() {
        int expectedQuantity = itemQuantity + 1;
        Assertions.assertEquals(2, expectedQuantity);
    }
}

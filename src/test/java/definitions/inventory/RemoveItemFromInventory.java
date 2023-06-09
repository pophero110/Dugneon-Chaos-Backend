package definitions.inventory;

import com.dungeonchaos.dungeonchaos.DungeonChaosApplication;
import com.dungeonchaos.dungeonchaos.model.Inventory;
import com.dungeonchaos.dungeonchaos.model.InventoryItem;
import com.dungeonchaos.dungeonchaos.request.InventoryRequest;
import com.dungeonchaos.dungeonchaos.service.InventoryService;
import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DungeonChaosApplication.class)
public class RemoveItemFromInventory {

    private final Logger log = LoggerFactory.getLogger(GenerateBoard.class);
    private static final String BASE_URL = "http://localhost:";

    private static ResponseEntity response;

    @LocalServerPort
    String port;

    private Inventory inventory;

    @Autowired
    private InventoryService inventoryService;

    private Long itemId;

    private int originalItemQuantity;

    HashMap<String, Object> inventoryMap;
    @Given("an inventory of ID {int} with item is available")
    public void anInventoryOfIDWithItemIsAvailable(int inventoryId) {
        this.inventory = inventoryService.getInventoryById(Long.valueOf(inventoryId));
        Assertions.assertNotNull(this.inventory);
    }

    @And("an item with ID {int}")
    public void anItemWithID(int itemId) {
        Optional<InventoryItem> inventoryItem = inventory.getInventoryItems().stream().filter(i -> i.getItem().getId() == itemId).findFirst();
        Assertions.assertTrue(inventoryItem.isPresent());
        originalItemQuantity = inventoryItem.get().getItemQuantity();
        this.itemId = Long.valueOf(itemId);
    }

    @When("I sends a PUT request to {string} with the item ID")
    public void theClientSendsADELETERequestToWithTheItemID(String endpoint) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            // Create the request body
            InventoryRequest inventoryRequest = new InventoryRequest(itemId);

            HttpEntity<InventoryRequest> requestEntity = new HttpEntity<>(inventoryRequest, headers);

            response = new RestTemplate().exchange(BASE_URL + port + endpoint, HttpMethod.PUT, requestEntity, String.class);
            Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }


    @Then("the response body should contain the updated inventory with item details")
    public void theResponseBodyShouldContainTheUpdatedInventoryWithItemDetails() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {};
            inventoryMap = objectMapper.readValue((String) response.getBody(), typeRef);

            Assert.assertNotNull(inventoryMap);
            Assert.assertEquals(inventoryMap.get("id"), 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @And("the quantity of the item should decrease by one")
    public void theQuantityOfTheItemShouldDecreaseBy() {
        int expectedQuantity =( (ArrayList<HashMap<String, Integer>>)inventoryMap.get("inventoryItems")).get(0).get("itemQuantity");
        originalItemQuantity--;
        Assertions.assertEquals(originalItemQuantity, expectedQuantity);
    }

}

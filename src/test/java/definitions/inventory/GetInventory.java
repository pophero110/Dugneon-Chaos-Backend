package definitions.inventory;

import com.dungeonchaos.dungeonchaos.DungeonChaosApplication;
import com.dungeonchaos.dungeonchaos.model.Inventory;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DungeonChaosApplication.class)
public class GetInventory {

    private final Logger log = LoggerFactory.getLogger(GenerateBoard.class);
    private static final String BASE_URL = "http://localhost:";

    private static ResponseEntity response;

    @LocalServerPort
    String port;


    @Autowired
    private InventoryService inventoryService;

    @Given("an inventory of ID {int} is available")
    public void anInventoryOfIDIsAvailable(int inventoryId) {
        Inventory inventory = inventoryService.getInventoryById(Long.valueOf(inventoryId));
        Assertions.assertNotNull(inventory);
    }

    @When("I sends a GET request to {string}")
    public void iSendsAGETRequestTo(String endpoint) {
        try {
            response = new RestTemplate().exchange(BASE_URL + port + endpoint, HttpMethod.GET, null, String.class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCodeValue(), expectedStatusCode);
    }

    @And("the response body should contain the inventory details")
    public void theResponseBodyShouldContainTheInventoryDetails() {
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
}

package definitions.player;

import com.dungeonchaos.dungeonchaos.DungeonChaosApplication;
import definitions.board.GetBoard;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DungeonChaosApplication.class)
public class CreatePlayer {

    private final Logger log = LoggerFactory.getLogger(GetBoard.class);
    private static final String BASE_URL = "http://localhost:";

    private static ResponseEntity response;

    @LocalServerPort
    String port;

    private LinkedHashMap<String, Object> responseBody;

    @Given("the player service is available")
    public void thePostEndpointIsAvailable() {
    }

    @When("I send a POST request to {string} with the following body:")
    public void iSendAPOSTRequestToWithTheFollowingBody(String endpoint, DataTable dataTable) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            // Extract the value from DataTable
            String selectedCharacterId = dataTable.cell(1, 0);

            // Create the request body
            String requestBody = "{\"selectedCharacterId\": \"" + selectedCharacterId + "\"}";

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            response = new RestTemplate().exchange(BASE_URL + port + endpoint, HttpMethod.POST, requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }

    @Then("the create player response status code should be {int}")
    public void theResponseStatusCodeShouldBe201(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCodeValue();
        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @And("the response body should contain a player object")
    public void theResponseBodyShouldContainAPlayerObject() {
        responseBody = JsonPath.from(String.valueOf(response.getBody())).get();
        assertNotNull(responseBody);
    }

    @And("the player object should have the following fields:")
    public void thePlayerObjectShouldHaveTheFollowingFields(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        assertTrue(responseBody.containsKey("id"));
        String expectedName = rows.get(0).get("name");
        String expectedHealth = rows.get(0).get("health");
        String expectedAttack = rows.get(0).get("attack");
        String expectedDefense = rows.get(0).get("defense");
        String expectedSpeed = rows.get(0).get("speed");

        assertEquals(expectedName, responseBody.get("name"));
        assertEquals(Integer.parseInt(expectedHealth), responseBody.get("health"));
        assertEquals(Integer.parseInt(expectedAttack), responseBody.get("attack"));
        assertEquals(Integer.parseInt(expectedDefense), responseBody.get("defense"));
        assertEquals(Integer.parseInt(expectedSpeed), responseBody.get("speed"));
    }
}

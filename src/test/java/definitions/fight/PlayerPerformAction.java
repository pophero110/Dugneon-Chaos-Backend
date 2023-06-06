package definitions.fight;

import com.dungeonchaos.dungeonchaos.DungeonChaosApplication;
import com.dungeonchaos.dungeonchaos.request.FightRequest;
import definitions.board.GetBoard;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DungeonChaosApplication.class)
public class PlayerPerformAction {

    private final Logger log = LoggerFactory.getLogger(GetBoard.class);
    private static final String BASE_URL = "http://localhost:";

    private static ResponseEntity response;

    @LocalServerPort
    String port;

    private Long fightId;
    @Given("a fight with ID {string}")
    public void aFightWithID(String fightId) {
        // from seed runner
        this.fightId = Long.valueOf(fightId);
    }

    @When("the player performs an action of type {string} in the fight")
    public void thePlayerPerformsAnActionOfTypeInTheFight(String actionType) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            // Create the request body
            String requestBody = "{\"actionType\": \"" + actionType + "\"}";

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            response = new RestTemplate().exchange(BASE_URL + port + "/api/fights/" + fightId + "/playerPerformAction", HttpMethod.PUT, requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }

    @Then("the player's action is processed in the fight")
    public void thePlayerSActionIsProcessedInTheFight() {
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}

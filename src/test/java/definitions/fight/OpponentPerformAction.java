package definitions.fight;

import com.dungeonchaos.dungeonchaos.DungeonChaosApplication;
import definitions.board.GetBoard;
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
public class OpponentPerformAction {
    private final Logger log = LoggerFactory.getLogger(GetBoard.class);
    private static final String BASE_URL = "http://localhost:";

    private static ResponseEntity response;

    @LocalServerPort
    String port;
    private static Long fightId = 1L;
    @When("the opponent performs an action in the fight")
    public void theOpponentPerformsAnActionInTheFight() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            HttpEntity<String> requestEntity = new HttpEntity<>("", headers);

            response = new RestTemplate().exchange(BASE_URL + port + "/api/fights/" + fightId + "/opponentPerformAction", HttpMethod.PUT, requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }

    @Then("the opponent's action is processed in the fight")
    public void theOpponentSActionIsProcessedInTheFight() {
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}

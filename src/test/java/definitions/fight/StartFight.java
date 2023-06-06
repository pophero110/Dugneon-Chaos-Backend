package definitions.fight;

import com.dungeonchaos.dungeonchaos.DungeonChaosApplication;
import com.dungeonchaos.dungeonchaos.repository.PlayerRepository;
import com.dungeonchaos.dungeonchaos.request.FightRequest;
import definitions.board.GetBoard;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DungeonChaosApplication.class)
public class StartFight {
    private final Logger log = LoggerFactory.getLogger(GetBoard.class);
    private static final String BASE_URL = "http://localhost:";

    private static ResponseEntity response;

    @LocalServerPort
    String port;
    private Long playerId;
    private Long monsterId;
    @Given("a player with ID {string} and a monster with ID {string}")
    public void aPlayerWithIDAndAMonsterWithID(String playerId, String monsterId) {
        // from seed runner
        this.playerId = Long.valueOf(playerId);
        this.monsterId = Long.valueOf(monsterId);
    }

    @When("the player starts a fight")
    public void thePlayerStartsAFight() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            // Create the request body
            FightRequest fightRequest = new FightRequest(this.playerId, this.monsterId);

            HttpEntity<FightRequest> requestEntity = new HttpEntity<>(fightRequest, headers);

            response = new RestTemplate().exchange(BASE_URL + port + "/api/fights/start", HttpMethod.POST, requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }

    @Then("a fight is initiated between the player and the monster")
    public void aFightIsInitiatedBetweenThePlayerAndTheMonster() {
        Assert.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }
}

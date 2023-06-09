package definitions.fight;

import com.dungeonchaos.dungeonchaos.DungeonChaosApplication;
import com.dungeonchaos.dungeonchaos.model.Fight.Fight;
import com.dungeonchaos.dungeonchaos.model.Fight.FightResult;
import com.dungeonchaos.dungeonchaos.repository.FightRepository;
import definitions.board.GenerateBoard;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DungeonChaosApplication.class)
public class PlayerWinFight {
    private final Logger log = LoggerFactory.getLogger(GenerateBoard.class);
    private static final String BASE_URL = "http://localhost:";

    private static ResponseEntity response;

    @LocalServerPort
    String port;
    private static Long fightId = 1L;

    @Autowired
    private FightRepository fightRepository;

    @When("the player wins the fight")
    public void thePlayerWinsTheFight() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            Optional<Fight> fight = fightRepository.findById(fightId);
            if(fight.isPresent()) {
                fight.get().setFightResult(FightResult.VICTORY_PLAYER);
                fightRepository.save(fight.get());
            }

            HttpEntity<String> requestEntity = new HttpEntity<>("", headers);

            response = new RestTemplate().exchange(BASE_URL + port + "/api/fights/" + fightId + "/playerWinFight", HttpMethod.POST, requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }

    @Then("a reward is generated for the player")
    public void aRewardIsGeneratedForThePlayer() {
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }
}

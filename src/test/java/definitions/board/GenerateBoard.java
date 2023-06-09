package definitions.board;


import com.dungeonchaos.dungeonchaos.DungeonChaosApplication;
import com.dungeonchaos.dungeonchaos.request.BoardRequest;
import com.dungeonchaos.dungeonchaos.request.FightRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DungeonChaosApplication.class)
public class GenerateBoard {

    private final Logger log = LoggerFactory.getLogger(GenerateBoard.class);
    private static final String BASE_URL = "http://localhost:";

    private static ResponseEntity response;

    @LocalServerPort
    String port;

    private Long playerId;

    @Given("A player is available")
    public void aPlayerIsAvailable() {
        this.playerId = 1L;
    }

    @When("the player fetch the board")
    public void thePlayerFetchTheBoard() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            // Create the request body
            BoardRequest boardRequest = new BoardRequest(this.playerId);

            HttpEntity<BoardRequest> requestEntity = new HttpEntity<>(boardRequest, headers);

            response = new RestTemplate().exchange(BASE_URL + port + "/api/boards", HttpMethod.POST, requestEntity, String.class);
            Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        } catch (HttpClientErrorException e) {

            e.printStackTrace();
        }
    }


    @Then("The board is returned")
    public void theBoardIsReturn() {
        List<String> board = JsonPath.from(String.valueOf(response.getBody())).get("board");
        Assert.assertTrue(!board.isEmpty());
    }
}

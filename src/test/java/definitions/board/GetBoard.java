package definitions.board;


import com.dungeonchaos.dungeonchaos.DungeonChaosApplication;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DungeonChaosApplication.class)
public class GetBoard {

    private final Logger log = LoggerFactory.getLogger(GetBoard.class);
    private static final String BASE_URL = "http://localhost:";

    private static ResponseEntity response;

    @LocalServerPort
    String port;

    //TODO: Refactor
    @Given("A board is available")
    public void aBoardIsAvailable() {
    }

    @When("I fetch the board")
    public void iFetchTheBoard() {
        try {
            response = new RestTemplate().exchange(BASE_URL + port + "/api/boards", HttpMethod.GET, null, String.class);
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

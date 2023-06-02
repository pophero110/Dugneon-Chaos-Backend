package definitions.character;

import com.dungeonchaos.dungeonchaos.DungeonChaosApplication;
import com.dungeonchaos.dungeonchaos.model.Character;
import com.dungeonchaos.dungeonchaos.repository.CharacterRepository;
import definitions.board.GetBoard;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DungeonChaosApplication.class)
public class GetCharacters {
    private final Logger log = LoggerFactory.getLogger(GetBoard.class);
    private static final String BASE_URL = "http://localhost:";

    @LocalServerPort
    String port;

    private static ResponseEntity response;
    private List<LinkedHashMap> characters;

    @Autowired
    private CharacterRepository characterRepository;

    @DataTableType
    public Character characterTableEntry(Map<String, String> entry) {
        // Convert the map to a CharacterTableEntry object
        String name = entry.get("name");
        String health = entry.get("health");
        String attack = entry.get("attack");
        String defense = entry.get("defense");
        String speed = entry.get("speed");

        // Create and return the CharacterTableEntry object
        return new Character(name, Integer.parseInt(health) , Integer.parseInt(attack), Integer.parseInt(defense), Integer.parseInt(speed));
    }

    @Given("A list of characters is available")
    public void aListOfCharactersIsAvailable() {
        List<Character> characters = characterRepository.findAll();
        assertEquals(!characters.isEmpty(), true);
    }


    @When("I send a GET request to {string}")
    public void iSendAGETRequestTo(String endpoint) {
        try {
            response = new RestTemplate().exchange(BASE_URL + port + endpoint, HttpMethod.GET, null, String.class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }

    @Then("the get characters response status code should be {int}")
    public void theResponseStatusCodeShouldBe200(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCodeValue();
        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @And("the response body should contain a list of characters")
    public void theResponseBodyShouldContainAListOfCharacters() {
        characters = JsonPath.from(String.valueOf(response.getBody())).get();
        assertNotNull(characters);
    }


    @And("the characters should have the following fields:")
    public void theCharactersShouldHaveTheFollowingFields(List<Character> expectedCharacters) {
        List<LinkedHashMap> actualCharacters = characters;

        // Ensure the size of the lists match
        assertEquals(expectedCharacters.size(), actualCharacters.size());

        // Iterate over the characters and compare the fields
        for (int i = 0; i < expectedCharacters.size(); i++) {
            Character expectedCharacter = expectedCharacters.get(i);
            LinkedHashMap<String, Object> actualCharacter = actualCharacters.get(i);

            assertEquals(expectedCharacter.getName(), actualCharacter.get("name"));
            assertEquals(expectedCharacter.getHealth(), actualCharacter.get("health"));
            assertEquals(expectedCharacter.getAttack(), actualCharacter.get("attack"));
            assertEquals(expectedCharacter.getDefense(), actualCharacter.get("defense"));
            assertEquals(expectedCharacter.getSpeed(), actualCharacter.get("speed"));
        }
    }
}

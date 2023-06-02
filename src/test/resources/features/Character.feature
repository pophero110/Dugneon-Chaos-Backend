Feature: Character Rest API functionalities

  Scenario: Get all characters
    Given A list of characters is available
    When I send a GET request to "/api/characters"
    Then the response status code should be 200
    And the response body should contain a list of characters
    And the characters should have the following fields:
      | id   | name    | health | attack | defense | speed |
      | 1    | Warrior | 90     | 10     | 20      | 30    |
      | 2    | Rogue   | 80     | 15     | 10      | 40    |

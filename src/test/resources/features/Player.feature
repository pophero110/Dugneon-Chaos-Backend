Feature: Player Rest API functionalities

  Scenario: Successfully create a player
    Given the player service is available
    When I send a POST request to "/api/players" with the following body:
      | selectedCharacterId |
      | 1                   |
    Then the create player response status code should be 201
    And the response body should contain a player object
    And the player object should have the following fields:
      | name    | health | attack | defense | speed |
      | Warrior | 90     | 10     | 20      | 30    |
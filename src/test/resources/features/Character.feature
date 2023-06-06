Feature: Character Rest API functionalities

  Scenario: Get all characters
    Given A list of characters is available
    When I send a GET request to "/api/characters"
    Then the get characters response status code should be 200
    And the response body should contain a list of characters

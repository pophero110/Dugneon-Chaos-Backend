Feature: Board Rest API functionalities

  Scenario: Generate a board
    Given A player is available
    When the player fetch the board
    Then The board is returned
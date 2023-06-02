Feature: Board Rest API functionalities

  Scenario: User able to fetch a board
    Given A board is available
    When I fetch the board
    Then The board is returned
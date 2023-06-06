Feature: Fight Rest API functionality

  Scenario: Starting a fight
    Given a player with ID "1" and a monster with ID "1"
    When the player starts a fight
    Then a fight is initiated between the player and the monster

  Scenario: Player performs an action in a fight
    Given a fight with ID "1"
    When the player performs an action of type "ATTACK" in the fight
    Then the player's action is processed in the fight

  Scenario: Opponent performs an action in a fight
    Given a fight with ID "1"
    When the opponent performs an action in the fight
    Then the opponent's action is processed in the fight

  Scenario: Player wins a fight
    Given a fight with ID "1"
    When the player wins the fight
    Then a reward is generated for the player

Feature: InventoryItem REST API functionalities

  Scenario: Equipping a weapon
    Given the inventory item is available and it's a unequipped weapon
    When the player equips the weapon
    Then the player's attributes should be updated with the weapon attributes
    And the weapon is marked as equipped

  Scenario: Equipping armor
    Given the inventory item is available and it's a unequipped armor
    When the player equips the armor
    Then the player's attributes should be updated with the armor attributes
    And the armor is marked as equipped

  Scenario: Unequipping a weapon
    Given the inventory item is available and it's a equipped weapon
    When the player unequips the weapon
    Then the player's attributes should be decreased with the weapon attributes
    And the weapon is marked as unequipped
#
  Scenario: Unequipping armor
    Given the inventory item is available and it's a equipped armor
    When the player unequips the armor
    Then the player's attributes should be decreased with the armor attributes
    And the armor is marked as unequipped

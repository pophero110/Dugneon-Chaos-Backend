Feature: Inventory Rest API functionalities

  Scenario: Get inventory by ID
    Given an inventory of ID 1 is available
    When I sends a GET request to "/api/inventories/1"
    Then the response status code should be 200
    And the response body should contain the inventory details

  Scenario: Add item to inventory by increasing quantity by one
    Given an inventory with ID 1 with item is available
    And an item ID of 1
    When I sends a POST request to "/api/inventories/1/addItem" with the item ID
    Then the response body should contain the updated inventory details
    And the quantity of the item should increase by one

  Scenario: Add item to inventory and create inventoryItem record if item has not been added
    Given an inventory of ID 2 without item
    When I sends a POST request to "/api/inventories/2/addItem" with the item ID of 2
    Then the response body should contain the added item
    And the inventoryItem should be created in database with quantity of 1

  Scenario: Remove item from inventory by decreasing quantity by one
    Given an inventory of ID 3 with item is available
    And an item with ID 3
    When I sends a PUT request to "/api/inventories/3/removeItem" with the item ID
    Then the response body should contain the updated inventory with item details
    And the quantity of the item should decrease by one

  Scenario: Remove item from inventory and database if quantity is 1
    Given an inventory of ID 1 with the item is available
    When I sends a PUT request to "/api/inventories/1/removeItem" with the the item ID 4
    Then the response body should not container the item
    And the item should be deleted in database

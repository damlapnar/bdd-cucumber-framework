Feature: Shopping Cart

  As a logged in user
  I want to manage items in my cart
  So that I can purchase products

  Background:
    Given I am logged in as "standard_user"
    And I am on the inventory page

  Scenario: Add a single item to cart
    When I add "Sauce Labs Backpack" to the cart
    Then the cart badge should show "1"

  Scenario: Add multiple items to cart
    When I add "Sauce Labs Backpack" to the cart
    And I add "Sauce Labs Bike Light" to the cart
    Then the cart badge should show "2"

  Scenario: Remove an item from cart
    Given I have "Sauce Labs Backpack" in my cart
    When I remove "Sauce Labs Backpack" from the cart
    Then the cart should be empty

  Scenario: Complete checkout flow
    Given I have "Sauce Labs Backpack" in my cart
    When I proceed to checkout
    And I fill in first name "Damla" last name "Pinar" postal code "10001"
    And I complete the purchase
    Then I should see the order confirmation message

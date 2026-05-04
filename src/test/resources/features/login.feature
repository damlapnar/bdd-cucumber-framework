Feature: User Login

  As a registered user
  I want to log in to the application
  So that I can access my account

  Background:
    Given I am on the login page

  Scenario: Successful login with valid credentials
    When I enter username "standard_user" and password "secret_sauce"
    And I click the login button
    Then I should be redirected to the inventory page

  Scenario: Failed login with invalid credentials
    When I enter username "invalid_user" and password "wrong_pass"
    And I click the login button
    Then I should see an error message containing "do not match"

  Scenario: Login attempt with empty username
    When I enter username "" and password "secret_sauce"
    And I click the login button
    Then I should see an error message containing "Username is required"

  Scenario Outline: Multiple invalid login attempts
    When I enter username "<username>" and password "<password>"
    And I click the login button
    Then I should see an error message containing "<error>"

    Examples:
      | username         | password     | error            |
      | locked_out_user  | secret_sauce | locked out       |
      | standard_user    |              | Password is required |
      | invalid          | invalid      | do not match     |

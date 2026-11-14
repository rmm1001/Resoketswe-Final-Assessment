Feature: Login

  Scenario Outline: Outline Login to Ndosi Automation Website
    Given I am on home page
    And I click login button
    And I enter username <username>
    And I enter password <password>
    When I click submit button
    Then I should be logged in successfully

    Examples:
      | username | password |
      | mashabela96@gmail.com | Test@1234 |
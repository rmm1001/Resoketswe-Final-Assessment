Feature: New User Registration

  Scenario Outline: Register a new user selecting the "Do Not Use" group
    Given I am on the home page
    And I click on the registration link
    And I fill in the registration form with valid details
    When I submit the registration form
    Then I should see a confirmation message indicating successful registration

    Examples:
      | firstname | lastname | email | password | confirmPassword | group |
      | John | Doe | john.doe@gmail.com | Test@1234 | Test@1234 | Do Not Use |



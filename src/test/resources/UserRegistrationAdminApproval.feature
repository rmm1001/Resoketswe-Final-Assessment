Feature: User Registration and Admin Approval

  Scenario: Register a new user, approve and promote to Admin
    Given I am on the registration page
    When I register a new user selecting the Do Not Use 2026 group
    Then the user registration should be submitted
    Given an existing admin logs into the application
    When the admin opens user management
    And the admin approves the new user
    And the admin promotes the new user to Admin
    Given I have logged out of the admin account
    When I log in with the newly registered user
    Then I should have the "Admin" role
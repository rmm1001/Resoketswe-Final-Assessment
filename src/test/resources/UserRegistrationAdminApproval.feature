
Feature: New User Registration and Admin Approval

  Scenario: Register a new user selecting the Do Not Use group
    Given I open the application home page
    When I click on Login
    And I click on Sign up here
    And I enter unique user details
    And I select the "Do Not Use" group
    And I submit the registration form
    Then my registration should be successful

  Scenario: Admin approves the new user
    Given an existing admin logs into the application
    When the admin navigates to user management
    And the admin searches for the newly registered user
    And the admin approves the newly registered user
    Then the user account status should be "Approved"

  Scenario: Admin assigns administrator role
    Given an existing admin logs into the application
    When the admin navigates to user management
    And the admin searches for the newly registered user
    And the admin updates the newly registered user's role
    And the admin assigns the "Admin" role
    And the admin saves the user changes
    Then the newly registered user should have the "Admin" role

  Scenario: Login with the newly registered user
    Given I have logged out of the admin account
    When I log in using the newly registered user credentials
    Then I should be successfully logged in
    And I should see the newly registered user's details

  Scenario: Verify administrator privileges
    And I should have the "Admin" role
    And I should have access to administrator functionality

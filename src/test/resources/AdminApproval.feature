Feature: Admin Approval

  Scenario Outline: Admin approves the new user
    Given an existing admin logs into the application
    When the admin navigates to user management
    And the admin searches for the newly registered user
    And the admin approves the newly registered user
    Then the user account status should be "Approved"
    Examples:
      |  |

  Scenario Outline: Admin assigns administrator privileges
    Given an existing admin logs into the application
    When the admin navigates to user management
    And the admin searches for the newly registered user
    When the admin updates the newly registered user's role
    And the admin assigns the "Admin" role
    And the admin saves the user changes
    Then the newly registered user should have the "Admin" role
    Examples:
      |  |

  Scenario Outline: Login with the newly registered user
    Given I have logged out of the admin account
    When I log in using the newly registered user credentials
    Examples:
      |  |

  Scenario Outline: Verify successful login
    Then I should be successfully logged in
    And I should see the newly registered user's details
    Examples:
      |  |
  Scenario Outline: Verify administrator privileges
    And I should have the "Admin" role
    And I should have access to administrator functionality
    Examples:
      |  |
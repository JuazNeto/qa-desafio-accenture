Feature: Web Tables Management
  This feature tests the creation and deletion of records in the Web Tables page.

  Scenario: Add and delete records in Web Tables
    Given I am on the Web Tables page
    When I create 12 new records
    Then I delete all new records
    And I close the browser

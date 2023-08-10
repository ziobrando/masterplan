Feature: Simple Planning

  Scenario: Finding time for a commercial call
    Given "Mario" is a registered user
    And "Mario" is completely busy next week
    And "Mario" defined a recipe for Commercial Call
    When an opportunity pops up for a Commercial Call
    Then the first available date should be during the following Monday morning
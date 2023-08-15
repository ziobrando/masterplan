Feature: Simple Planning


  Scenario: Virgin users availability
    Given "Freshman" is a registered user
    And no calendar has been associated with "Freshman"
    Then "Freshman" should be available for a meeting next "Monday"



  Scenario: Finding time for a commercial call
    Given "Mario" is a registered user
    And "Mario" is completely busy next week
    And "Mario" defined a recipe for Commercial Call
    When an opportunity pops up for a Commercial Call
    Then the first available date should be during the following Monday morning
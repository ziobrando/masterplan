Feature: Simple Planning

  Scenario: New users availability
    Given "Justin Signed" is a registered user
    And no calendar has been associated with "Justin Signed"
    Then "Justin Signed" should be available for a meeting next "Monday"




  Scenario: Finding time for a commercial call
    Given "Mario" is a registered user
    And "Mario" is completely busy next week
    And "Mario" defined a recipe for Commercial Call
    When an opportunity pops up for a Commercial Call
    Then the first available date should be during the following Monday morning
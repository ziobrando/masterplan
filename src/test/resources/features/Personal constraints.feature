Feature: Personal constraints

  Scenario: Basic employee constraints
    Given "Hannah" is a registered user
    When "Hannah" sets up a 80 priority constraint for not working on weekends
    And "Hannah" sets up a 70 priority constraint for working only from 9:00 to 18:00
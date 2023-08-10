Feature: Basic configuration

  Scenario: Simple user registration
    When user "Mario" signs up
    Then "Mario" should be a busy person
    And "Mario" should have an availability calendar

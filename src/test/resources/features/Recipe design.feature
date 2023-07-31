Feature: Recipe design

  Background:
    Given "Mario" is a registered user

  Scenario: Basic Meeting
    When "Mario" defines a recipe called "Standard Meeting"
    And "Mario" adds a 45 min (moment) called "Meeting" to "Standard Meeting"
    And "Mario" adds a 15 min (moment) called "Recovery" to "Standard Meeting"
    Then recipe "Standard Meeting" should include 2 (moments)
    And time allocated for a "Standard Meeting" should be 1 hour


  Scenario: Commercial Call
    When "Mario" defines a recipe called "Commercial Call"
    And "Mario" adds a 45 min (moment) called "Call" to "Commercial Call"
    And "Mario" adds a 30 min (moment) called "Note Taking" right after "Commercial Call"
    And "Mario" adds a 15 min (moment) called "Preparation" right before "Commercial Call"
    Then recipe "Commercial Call" should include 3 (moments)
    And time allocated for a "Commercial Call" should be 1 hour and 30 minutes
    And footprint for "Commercial Call" should start 15 minutes before the official start


Feature: Personal Availability

  Scenario: Basic stuff
    Given "Mario" is a registered user
    Then "Mario" should have a personal calendar

    Scenario: Basic working hours
      Given "Mario" is a registered user
      And "Mario" defined a "Working Hours" constraint from "9:00" to "18:00"



    Scenario: Simple event allocation
      Given "Mario" is a registered user
      And "Mario"
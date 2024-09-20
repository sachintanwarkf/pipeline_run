@DemoFeature
Feature: Test Demo
  Background:
    Given Initialize the "chrome" browser
    Given User open the url "https://google.co.in"

  Scenario: Searching the result
    Then User wait for "2" seconds
    Then User enter "Name" in "ip_txtSearch" on "LANDING_PAGE"

#    Then User enter "icuser2@kashyaptummidigmail.onmicrosoft.com" in "text_Email" on "LANDING_PAGE"
#    Then User clicks "btn_Login" on "LANDING_PAGE"
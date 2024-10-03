Feature: Testing apis
  Background:
    Given Set the base URI
    Given Setting up of DB

  Scenario: To verify GET apis ( When client id is not present )
    Then Set the end point to "getProjects"
    When Send a GET request
    Then Verify response status code is 400
    Then Store the response as a string

  Scenario: To verify GET apis ( When client id is present )
    Then Set the end point to "getProjects"
    Then Add query parameters:
      |key        |value  |
      |clientId   |123456 |
    When Send a GET request
    Then Verify response status code is 200
    Then Store the response as a list of maps
    Then Get the values from db
    Then Compare the values of api with db

  Scenario: To verify GET apis ( When client id is present,along with different dates are present, project type KF Asses  )
    Then Set the end point to "getProjects"
    Then Add query parameters:
      |key          |value      |
      |fromDate     |2024-12-31 |
      |toDate       |2024-12-31 |
      |projectType  |KF Asses   |
      |clientId     |123456     |
    When Send a GET request
    Then Verify response status code is 200
    Then Store the response as a list of maps
    Then Get the values from db
    Then Compare the values of api with db


  Scenario: To verify GET apis ( When client id is present,along with different dates are present, project type KF Select  )
    Then Set the end point to "getProjects"
    Then Add query parameters:
      |key          |value      |
      |clientId     |123456     |
      |fromDate     |2024-12-31 |
      |toDate       |2024-12-31 |
      |projectType  |KF Select  |
    When Send a GET request
    Then Verify response status code is 200
    Then Store the response as a list of maps
    Then Get the values from db
    Then Compare the values of api with db

  Scenario: To verify GET apis ( When client id is present,along with different dates are present, both project types  )
    Then Set the end point to "getProjects"
    Then Add query parameters:
      |key            |value      |
      |clientId       |123456     |
      |fromDate       |2024-12-31 |
      |toDate         |2024-12-31 |
      |projectType    |["KF Select","KF Asses"]  |
    When Send a GET request
    Then Verify response status code is 200
    Then Store the response as a list of maps
    Then Get the values from db
    Then Compare the values of api with db
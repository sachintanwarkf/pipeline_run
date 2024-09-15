Feature: Testing apis
  Background:
    Given Set the base URI to "https://insights-api.int.kfone-dev.com"

  Scenario: To verify GET apis ( When client id is not present )
    Then Set the end point to "/projects/getProjects"
    When Send a GET request
    Then Verify response status code is 400
    Then Store the response as a map

  Scenario: To verify GET apis ( When client id is present )
    Then Set the end point to "/projects/getProjects"
    Then Add query parameters:
      |key|value|
      |clientId   |123456 |
    When Send a GET request
    Then Verify response status code is 200
    Then Store the response as a map

  Scenario: To verify GET apis ( When client id is present,along with different dates are present, project type KF Asses  )
    Then Set the end point to "/projects/getProjects"
    Then Add query parameters:
      |key          |value      |
      |fromDate     |2024-12-31 |
      |toDate       |2024-12-31 |
      |projectType  |KF Asses   |
      |clientId     |123456     |
    When Send a GET request
    Then Verify response status code is 200
    Then Store the response as a map

  Scenario: To verify GET apis ( When client id is presen,along with different dates are present, project type KF Select  )
    Then Set the end point to "/projects/getProjects"
    Then Add query parameters:
      |key          |value      |
      |clientId     |123456     |
      |fromDate     |2024-12-31 |
      |toDate       |2024-12-31 |
      |projectType  |KF Select  |
    When Send a GET request
    Then Verify response status code is 200
    Then Store the response as a map

#  Scenario: To verify GET apis ( When client id is presen,along with different dates are present, both project types  )
#    Then Set the end point to "/projects/getProjects"
#    Then Add query parameters:
#      |key            |value      |
#      |clientId       |123456     |
#      |fromDate       |2024-12-31 |
#      |toDate         |2024-12-31 |
#      |projectType    |KF Select  |
#      |projectType    |KF Asses   |
#    When Send a GET request
#    Then Verify response status code is 200
#    Then Store the response as a map

  Scenario: To verify POST request ( Get the project type distributtion )
    Then Set the end point to "/api/v1/dashboard/projectctypedistribution"
    When Add Request Body Type: "application/json"
    When Add body:
      """
      {
        "clientId": 53,
        "startDate": null,
        "endDate": null,
        "dateRangeStr": "last three months",
        "productTypes": "assess"
      }
      """
    When Send a POST request
    When Store the response as a map
    Then Verify response status code is 201

  Scenario: To verify POST request ( Get the project participant distributtion )
    Then Set the end point to "/api/v1/dashboard/projectparticipantdistribution"
    When Add Request Body Type: "application/json"
    When Add body:
      """
      {
        "clientId": 53,
        "startDate": null,
        "endDate": null,
        "dateRangeStr": "last three months",
        "productTypes": "assess"
      }
      """
    When Send a POST request
    When Store the response as a map
    Then Verify response status code is 201

  Scenario: To verify POST request ( Get the SP norms type distributtion )
    Then Set the end point to "/api/v1/dashboard/spnormsdistribution"
    When Add Request Body Type: "application/json"
    When Add body:
      """
      {
        "clientId": 53,
        "startDate": null,
        "endDate": null,
        "dateRangeStr": "last three months",
        "productTypes": "assess"
      }
      """
    When Send a POST request
    When Store the response as a map
    Then Verify response status code is 201
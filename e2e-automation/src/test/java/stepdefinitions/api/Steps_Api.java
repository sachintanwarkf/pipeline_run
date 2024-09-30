package stepdefinitions.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import helpers.utilities.ConfigProperties;
import helpers.utilities.db.DbApiComparatorClass;
import helpers.utilities.db.ProjectDataFetcher;
import helpers.utilities.db.DbHandler;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.awaitility.Awaitility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Steps_Api {

    private static final Logger LOGGER = LoggerFactory.getLogger(Steps_Api.class);

    private RequestSpecification requestSpec;
    private Response response;
    private String endpoint;
    Map<String, Object> responseMap;
    private List<Map<String, Object>> responseList;
    private String responseString;
    ConfigProperties cf=new ConfigProperties();
    private DbHandler db = new DbHandler();
    ProjectDataFetcher dataFetcher;
    Connection connection;
    Map<Object, Map<String, Object>> projectDataMap;


    @Given("^Set the base URI$")
    public void setBaseURI() {
        String baseURI = cf.getProperty("BASE_URI");
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = baseURI;
        requestSpec = new RequestSpecBuilder().build(); // Initialize requestSpec
        LOGGER.info("Base Uri: "+baseURI);
    }

    @Then("^Set the end point to \"([^\"]*)\"$")
    public void setEndPoint(String project) {
        String configPropertyValue="endpoint_"+project;
        String endPoint = cf.getProperty(configPropertyValue);
        endpoint = endPoint;
        LOGGER.info("End point: "+endPoint);
    }

    @When("^Add path parameters with keyword \"([^\"]*)\" and value \"([^\"]*)\"$")
    public void addPathParameters(String id, String value) {
        requestSpec = new RequestSpecBuilder()
                .addPathParam(id, value)  // Add the path parameter
                .build();  // Build the RequestSpecification only once
    }

    @When("^Add query parameters:$")
    public void addQueryParameters(Map<String, String> queryParams) {
        RequestSpecBuilder builder = new RequestSpecBuilder().addQueryParams(queryParams);
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            requestSpec.param(entry.getKey(), entry.getValue());
        }
    }

    @When("^Add header parameters:$")
    public void addHeaderParameters(Map<String, String> headers) {
        RequestSpecBuilder builder = new RequestSpecBuilder().addHeaders(headers);
        requestSpec = builder.build();
    }

    @When("^Add body:$")
    public void addBody(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String cleanedBody = body.trim().replace("'", "\"");
        Object bodyObject = objectMapper.readValue(cleanedBody, Object.class);
        requestSpec = requestSpec.body(bodyObject);
    }

    @When("^Add body from file: \"([^\"]*)\"$")
    public void addBodyFromJsonFile(String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String filePath = "";
        if(fileName.contentEquals("Project Type Distribution")){
            filePath=System.getProperty("user.dir")+"/src/test/resources/testDataResources/api/body/project_type_distribution.json";
        } else if(fileName.contentEquals("Project Participant Distribution")){
            filePath=System.getProperty("user.dir")+"/src/test/resources/testDataResources/api/body/project_participant_distribution.json";
        } else if(fileName.contentEquals("Sp Norms Distribution")){
            filePath=System.getProperty("user.dir")+"/src/test/resources/testDataResources/api/body/norms_type_distribution.json";
        }
        File jsonFile = new File(filePath);
        Object bodyObject = objectMapper.readValue(jsonFile, Object.class);
        requestSpec = requestSpec.body(bodyObject);
    }

    @When("^Add Request Body Type: \"([^\"]*)\"$")
    public void addRequestBody(String bodyType) throws JsonProcessingException {
        requestSpec = requestSpec.contentType(bodyType);
    }

    @When("^Add authentication with type \"([^\"]*)\" and credentials \"([^\"]*)\"$")
    public void addAuthentication(String authType, String credentials) {
        switch (authType.toLowerCase()) {
            case "basic":
                String[] creds = credentials.split(":");
                requestSpec = requestSpec.auth().preemptive().basic(creds[0], creds[1]);
                break;
            case "bearer":
                requestSpec = requestSpec.auth().oauth2(credentials);
                break;
            default:
                throw new IllegalArgumentException("Unsupported auth type: " + authType);
        }
    }

    @When("^Send a (GET|POST|PUT|DELETE) request$")
    public void sendRequest(String method) {
        LOGGER.info("Api request: "+requestSpec);

        switch (method.toUpperCase()) {
            case "GET":
                response = RestAssured.given(requestSpec).get(endpoint);
                break;
            case "POST":
                response = RestAssured.given(requestSpec).post(endpoint);
                break;
            case "PUT":
                response = RestAssured.given(requestSpec).put(endpoint);
                break;
            case "DELETE":
                response = RestAssured.given(requestSpec).delete(endpoint);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
    }

    @Then("^Store the response as a map$")
    public void storeResponseAsMap() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = response.getBody().asString();
        try {
            responseMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
            LOGGER.info("Response stored as Map: {}", responseMap);
        } catch (IOException e) {
            try {
                responseList = objectMapper.readValue(responseBody, new TypeReference<List<Map<String, Object>>>() {});
                LOGGER.info("Response stored as List of Maps: {}", responseList);
            } catch (IOException ex) {
                LOGGER.error("Error parsing response as Map or List of Maps: {}", ex.getMessage());
                throw ex;
            }
        }
    }

    @Then("^Store the response as a list of maps$")
    public void storeResponseAsListOfMaps() {
        ObjectMapper objectMapper = new ObjectMapper();  // Jackson ObjectMapper for JSON parsing
        String responseBody = response.getBody().asString();  // Get the response body as a String

        try {
            // Parse the JSON response into List<Map<String, Object>>
            responseList = objectMapper.readValue(responseBody, new TypeReference<List<Map<String, Object>>>(){});
            LOGGER.info("Response stored as List<Map<String, Object>>: {}", responseList);
        } catch (IOException e) {
            LOGGER.error("Failed to parse response into List<Map<String, Object>>: {}", e.getMessage());
        }
    }

    @Then("^Store the response as a string$")
    public void storeResponseAsString() {
        String responseBody = response.getBody().asString();
        this.responseString = responseBody;
        LOGGER.info("Response stored as String: {}", responseString);
    }

    @Then("^User checks (.+) in response$")
    public void verifyResponseContains(String expectedSubstring) {
        Assert.assertNotNull(responseString, "Response string is null or empty.");
        Assert.assertTrue(responseString.contains(expectedSubstring),
                "Expected substring not found in the response.");
        LOGGER.info("Verified that the response contains: {}", expectedSubstring);
    }

    @Then("^Verify response status code is (\\d+)$")
    public void verifyStatusCode(int statusCode) {
        // Wait for the response and verify the status code
        Awaitility.await()
                .atMost(180, TimeUnit.SECONDS) // Wait up to 180 seconds
                .pollInterval(1, TimeUnit.SECONDS) // Check every second
                .until(() -> response.getStatusCode() == statusCode); // Wait until status code matches

        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, statusCode, "Unexpected response status code.");
        LOGGER.info("Verified response status code: {}", statusCode);
    }

    @Then("^Print response$")
    public void printResponse() {
        LOGGER.info("Response: {}", response.asString());
    }

    @Then("^Setting up of DB$")
    public void settingUpOfDb() throws SQLException {
        LOGGER.info("Setting up the database...");
        connection=db.connectToDB();
    }

    @Then("^Get the values from db$")
    public void getDbValue() throws SQLException {
        DbHandler.switchSchema("idw");  // Switch to the appropriate schema if necessary
        // Create the ProjectDataFetcher and fetch the project details
        ProjectDataFetcher dataFetcher = new ProjectDataFetcher(connection);
        projectDataMap= dataFetcher.fetchAllProjects();
        LOGGER.info("Values from db "+ projectDataMap);
    }

    @Then("^Compare the values of api with db$")
    public void compareDbValueWithApiValue() throws SQLException {
        DbApiComparatorClass dbApiComparator=new DbApiComparatorClass();
        dbApiComparator.compareDbApiValues("engagementCode",responseList,projectDataMap);
    }
}

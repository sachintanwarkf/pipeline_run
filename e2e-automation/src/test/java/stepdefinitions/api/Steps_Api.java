package stepdefinitions.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.*;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Steps_Api {

    private RequestSpecification requestSpec;
    private Response response;
    private String endpoint;
    Map<String, Object> responseMap;
    private List<Map<String, Object>> responseList;

    @Given("^Set the base URI to \"([^\"]*)\"$")
    public void setBaseURI(String baseURI) {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = baseURI;
        requestSpec = new RequestSpecBuilder().build(); // Initialize requestSpec
    }

    @Then("^Set the end point to \"([^\"]*)\"$")
    public void setEndPoint(String endPoint) {
        endpoint = endPoint;
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
            System.out.println("Response as Map: " + responseMap);
        } catch (IOException e) {
            try {
                responseList = objectMapper.readValue(responseBody, new TypeReference<List<Map<String, Object>>>() {});
                System.out.println("Response as List of Maps: " + responseList);
            } catch (IOException ex) {
                System.err.println("Error parsing response as Map or List of Maps");
                throw ex;
            }
        }
    }

    @Then("^Verify response using keyword:\"([^\"]*)\" and value:\"([^\"]*)\"$")
    public void verifyResponseWithKeysAndValues(String keyword, String value) {
        Object result = null;
        String dataType = "";
        if (responseMap != null) {
            result = findValue(responseMap, keyword);
            dataType = getResultDataType(result);
        } else if (responseList != null) {
            result = findValueInList(responseList, keyword);
            dataType = getResultDataType(result);
        }
        if (result != null) {
            System.out.println("Datatype: " + dataType);
            Assert.assertEquals("Response value does not match expected value.", value, result.toString());
        } else {
            Assert.fail("Keyword not found in the response.");
        }
    }

    @Then("^Verify response status code is (\\d+)$")
    public void verifyStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("^Print response$")
    public void printResponse() {
        System.out.println(response.asString());
    }

    private Object findValue(Map<String, Object> map, String keyword) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals(keyword)) {
                return entry.getValue();
            } else if (entry.getValue() instanceof Map) {
                Object result = findValue((Map<String, Object>) entry.getValue(), keyword);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    private Object findValueInList(List<Map<String, Object>> list, String keyword) {
        for (Map<String, Object> map : list) {
            Object result = findValue(map, keyword);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private String getResultDataType(Object value) {
        return value.getClass().toString();
    }
}

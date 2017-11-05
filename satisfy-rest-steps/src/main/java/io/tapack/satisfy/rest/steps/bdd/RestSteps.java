package io.tapack.satisfy.rest.steps.bdd;

import com.google.common.io.Files;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import freemarker.template.TemplateException;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.tapack.util.FileUtils.getFileFromResourcesByFilePath;
import static io.tapack.util.SessionVariablesUtils.*;
import static io.tapack.util.TemplateUtils.processTemplate;
import static org.junit.Assert.assertEquals;

public class RestSteps {

    public static final String SEPARATOR = "=";
    public static final String KEY = "REST-RESPONSE";
    public static final String CUSTOM_HEADERS_FOR_NEXT_REQUEST_REST = "CUSTOM-HEADERS-FOR-NEXT-REQUEST-REST";
    public static final String CUSTOM_HEADERS_FOR_ALL_REQUESTS_REST = "CUSTOM-HEADERS-FOR-ALL-REQUESTS-REST";

    @Given("custom header '$name' with value '$value' for next request")
    public void givenCustomHeaderForNextRequest(String name, String value) {
        givenCustomHeaderFor(name, value, CUSTOM_HEADERS_FOR_NEXT_REQUEST_REST);
    }

    @Given("custom header '$name' with value '$value' for all requests")
    public void givenCustomHeaderForAllRequests(String name, String value) {
        givenCustomHeaderFor(name, value, CUSTOM_HEADERS_FOR_ALL_REQUESTS_REST);
    }

    private void givenCustomHeaderFor(String name, String value, String key) {
        Map<String, String> headers = getVariableValue(key);
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put(name, value);
        save(key, headers);
    }

    @When("use GET '$path' with params '$param'")
    public void whenGetRequestWithParam(String path, List<String> args) {
        Map<String, String> params = splitKeyValue(args);
        Response response = given().parameters(params).get(path);
        save(KEY, response);
    }

    @When("use GET '$path' url")
    public void whenGetRequest(String path) {
        Response response = given().get(path);
        save(KEY, response);
    }

    @When("use POST '$path' with params '$param'")
    public void whenPostRequest(String path, List<String> args) {
        Map<String, String> params = splitKeyValue(args);
        Response response = given().parameters(params).post(path);
        save(KEY, response);
    }

    @When("use PUT '$path' with params '$param'")
    public void whenPutRequest(String path, List<String> args) {
        Map<String, String> params = splitKeyValue(args);
        Response response = given().parameters(params).put(path);
        save(KEY, response);
    }

    @When("use DELETE '$path' with params '$param'")
    public void whenDeleteRequestWithParams(String path, List<String> args) {
        Map<String, String> params = splitKeyValue(args);
        Response response = given().parameters(params).delete(path);
        save(KEY, response);
    }

    @When("use DELETE '$path' url")
    public void whenDeleteRequest(String path) {
        Response response = given().delete(path);
        save(KEY, response);
    }

    @Then("verify content type '$type' in REST response")
    public void thenResponseHasContentType(String type) {
        Response response = getVariableValue(KEY);
        assertEquals(type, response.contentType());
    }

    @Then("verify status code '$code' in REST response")
    public void thenResponseHasStatusCode(int code) {
        Response response = getVariableValue(KEY);
        assertEquals(code, response.getStatusCode());
    }

    @Then("verify REST response equals to '$body' in response")
    public void thenResponseEqualTo(String body) {
        Response response = getVariableValue(KEY);
        assertEquals(body, response.getBody().asString());
    }

    private Map<String, String> splitKeyValue(List<String> args) {
        Map<String, String> entries = new HashMap<>();
        if (args == null) return entries;
        for (String entry : args) {
            String[] split = entry.split(SEPARATOR);
            String key = split[0];
            String value = split[1];
            entries.put(key, value);
        }
        return entries;
    }

    @When("use PUT '$path' with body from '$file'")
    public void whenPutRequestWithBodyFrom(String path, String file) throws IOException, TemplateException {
        Response response = getRequestSpecificationFrom(file).put(path);
        save(KEY, response);
    }

    private RequestSpecification getRequestSpecificationFrom(String file) throws IOException, TemplateException {
        File fileFromResources = getFileFromResourcesByFilePath(file);
        RequestSpecification given = given();
        if (fileFromResources.getAbsolutePath().endsWith(".ftl")) {
            fileFromResources = processTemplate(fileFromResources);
        }
        if (fileFromResources.getAbsolutePath().endsWith(".xml")) {
            given.contentType(ContentType.XML);
        } else if (fileFromResources.getAbsolutePath().endsWith(".json")) {
            given.contentType(ContentType.JSON);
        }
        return given.body(Files.toByteArray(fileFromResources));
    }

    @When("use POST '$path' with body from '$file'")
    public void whenPostRequestWithBodyFrom(String path, String file) throws IOException, TemplateException {
        Response response = getRequestSpecificationFrom(file).post(path);
        save(KEY, response);
    }

    private RequestSpecification given() {
        Map<String, String> headersForOne = getVariableValue(CUSTOM_HEADERS_FOR_NEXT_REQUEST_REST);
        Map<String, String> headersForAll = getVariableValue(CUSTOM_HEADERS_FOR_ALL_REQUESTS_REST);
        RequestSpecification given = RestAssured.given();
        RestAssured.useRelaxedHTTPSValidation();
        if (headersForOne != null) {
            given.headers(headersForOne);
            removeVariable(CUSTOM_HEADERS_FOR_NEXT_REQUEST_REST);
        }
        if (headersForAll != null) {
            given.headers(headersForAll);
        }
        return given;
    }

}
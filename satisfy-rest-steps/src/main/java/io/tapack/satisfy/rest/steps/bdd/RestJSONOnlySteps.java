package io.tapack.satisfy.rest.steps.bdd;

import com.google.common.io.Files;
import com.jayway.restassured.response.Response;
import org.jbehave.core.annotations.Then;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static io.tapack.satisfy.rest.steps.bdd.RestSteps.KEY;
import static io.tapack.util.FileUtils.getFileFromResourcesByFilePath;
import static io.tapack.util.SessionVariablesUtils.getVariableValue;
import static io.tapack.util.SessionVariablesUtils.save;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static net.javacrumbs.jsonunit.JsonAssert.when;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_VALUES;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

public class RestJSONOnlySteps {

    @Then("verify REST response has '$jsonPath' JsonPath")
    public void thenCheckResponseByJsonPathOnPositive(String jsonPath) {
        Response response = getVariableValue(KEY);
        response.then().body(jsonPath, notNullValue());
    }

    @Then("verify REST response hasn't '$jsonPath' JsonPath")
    public void thenCheckResponseByJsonPathOnNegative(String jsonPath) {
        Response response = getVariableValue(KEY);
        response.then().body(jsonPath, nullValue());
    }

    @Then("verify REST response has '$jsonPath' JsonPath with '$value'")
    public void thenCheckResponseByJsonPathOnContains(String jsonPath, String value) {
        Response response = getVariableValue(KEY);
        String actual = response.then().extract().path(jsonPath).toString();
        assertThat(actual, containsString(value));
    }

    @Then("verify REST response hasn't '$jsonPath' JsonPath with '$value'")
    public void thenCheckResponseByJsonPathOnNotContains(String jsonPath, String value) {
        Response response = getVariableValue(KEY);
        String actual = response.then().extract().path(jsonPath).toString();
        assertThat(actual, not(containsString(value)));
    }

    @Then("verify REST-JSON response is equal to '$file'")
    public void thenJSONResponseEqualToFile(String file) throws IOException, SAXException {
        File fileFromResources = getFileFromResourcesByFilePath(file);
        String expectedJSON = Files.toString(fileFromResources, Charset.defaultCharset());
        Response response = getVariableValue(KEY);
        String actualJSON = response.getBody().asString();
        assertJsonEquals(expectedJSON, actualJSON);
    }

    @Then("verify REST-JSON response is similar to '$file'")
    public void thenJSONResponseSimilarToFile(String file) throws IOException, SAXException {
        File fileFromResources = getFileFromResourcesByFilePath(file);
        String expectedJSON = Files.toString(fileFromResources, Charset.defaultCharset());
        Response response = getVariableValue(KEY);
        String actualJSON = response.getBody().asString();
        assertJsonEquals(expectedJSON, actualJSON, when(IGNORING_VALUES));
    }

    @Then("get value from REST-JSON response by '$jsonPath' and save to '$variable'")
    public void getJSONPathFromLastRESTResponse(String jsonPath, String variable) throws IOException, SOAPException, ParserConfigurationException, SAXException, XPathExpressionException {
        Response response = getVariableValue(KEY);
        save(variable, response.path(jsonPath).toString());
    }
}

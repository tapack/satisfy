package io.tapack.satisfy.rest.steps.bdd;

import com.google.common.io.Files;
import com.jayway.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.IgnoreTextAndAttributeValuesDifferenceListener;
import org.custommonkey.xmlunit.XMLUnit;
import org.jbehave.core.annotations.Then;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import static io.tapack.satisfy.rest.steps.bdd.RestSteps.KEY;
import static io.tapack.util.FileUtils.getFileFromResourcesByFilePath;
import static io.tapack.util.SessionVariablesUtils.getVariableValue;
import static io.tapack.util.SessionVariablesUtils.save;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.xml.HasXPath.hasXPath;
import static org.junit.Assert.*;

public class RestXMLOnlySteps {

    @Then("verify REST response has '$xPath' XPath")
    public void thenCheckResponseByXpathOnPositive(String xPath) {
        Response response = getVariableValue(KEY);
        response.then().body(hasXPath(xPath));
    }

    @Then("verify REST response hasn't '$xPath' XPath")
    public void thenCheckResponseByXpathOnNegative(String xPath) {
        Response response = getVariableValue(KEY);
        response.then().body(not(hasXPath(xPath)));
    }

    @Then("verify REST response has '$xPath' XPath with '$value'")
    public void thenCheckResponseByXpathOnContains(String xPath, String value) {
        Response response = getVariableValue(KEY);
        response.then().body(hasXPath(xPath, containsString(value)));
    }

    @Then("verify REST response hasn't '$xPath' XPath with '$value'")
    public void thenCheckResponseByXpathOnNotContains(String xPath, String value) {
        Response response = getVariableValue(KEY);
        response.then().body(hasXPath(xPath, not(containsString(value))));
    }

    @Then("verify REST-XML response is equal to '$file'")
    public void thenXMLResponseEqualToFile(String file) throws IOException, SAXException {
        File fileFromResources = getFileFromResourcesByFilePath(file);
        List<String> expected = Files.readLines(fileFromResources, Charset.defaultCharset());
        String expectedXml = expected.stream()
                .map(StringUtils::chomp)
                .map(StringUtils::strip)
                .collect(Collectors.joining());
        Response response = getVariableValue(KEY);
        String actualXml = response.getBody().asString();
        XMLUnit.setIgnoreWhitespace(true);
        DetailedDiff myDiff = new DetailedDiff(new Diff(expectedXml, actualXml));
        assertThat(myDiff.toString(), actualXml, is(expectedXml));
    }

    @Then("verify REST-XML response is similar to '$file'")
    public void thenXMLResponseSimilarToFile(String file) throws IOException, SAXException {
        File fileFromResources = getFileFromResourcesByFilePath(file);
        List<String> expected = Files.readLines(fileFromResources, Charset.defaultCharset());
        String expectedXml = expected.stream()
                .map(StringUtils::chomp)
                .map(StringUtils::strip)
                .collect(Collectors.joining());
        Response response = getVariableValue(KEY);
        String actualXml = response.getBody().asString();
        Diff diff = new Diff(expectedXml, actualXml);
        diff.overrideDifferenceListener(new IgnoreTextAndAttributeValuesDifferenceListener());
        StringBuffer stringBuffer = diff.appendMessage(new StringBuffer());
        stringBuffer.append("\nExpected: ")
                .append(expectedXml)
                .append("\n     but: ")
                .append(actualXml)
                .append("\n");
        assertTrue(stringBuffer.toString(), diff.similar());
    }

    @Then("get value from REST-XML response by '$xpath' and save to '$variable'")
    public void getXPathFromLastRESTResponse(String xPath, String variable) throws IOException, SOAPException, ParserConfigurationException, SAXException, XPathExpressionException {
        Response response = getVariableValue(KEY);
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource source = new InputSource();
        source.setCharacterStream(new StringReader(response.getBody().asString()));
        Document doc = db.parse(source);
        XPath xpath = XPathFactory.newInstance().newXPath();
        Node node = (Node) xpath.evaluate(xPath, doc.getDocumentElement(), XPathConstants.NODE);
        assertNotNull("No element by Xpath: " + xPath + " was found", node);
        save(variable, node.getTextContent());
    }
}

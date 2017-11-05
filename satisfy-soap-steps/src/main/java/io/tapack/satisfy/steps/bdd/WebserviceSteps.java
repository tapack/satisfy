package io.tapack.satisfy.steps.bdd;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.tapack.satisfy.steps.helper.WebServiceProperties;
import net.thucydides.core.Thucydides;
import net.thucydides.core.annotations.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.IgnoreTextAndAttributeValuesDifferenceListener;
import org.custommonkey.xmlunit.XMLUnit;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import static io.tapack.satisfy.steps.helper.WebServiceHelper.*;
import static io.tapack.util.FileUtils.getFileFromResourcesByFilePath;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WebserviceSteps {

    private static final int LENGTH_XML_ENCLOSING_TAG = 12;
    private static final Logger LOGGER = LoggerFactory.getLogger(WebserviceSteps.class);
    public static final String WEB_SERVICE_SOAP_ACTION_KEY = "webServiceSoapAction";
    public static final String WEB_SERVICE_ENDPOINT_URL_KEY = "webServicesEndpointUrl";
    private static final String FAULT_STRING = "fault string";
    private static final String FAULT_ACTOR = "fault actor";
    private static final String FAULT_CODE = "fault code";
    private static final String HTTPS_PROTOCOL = "https";
    private static final String HTTP_PROTOCOL = "http";
    public static final String RESPONSE = "lastResponse";
    public static final String TABLE_2_COLUMNS = "<table><thead><tr><th>%s</th><th>%s</th></tr></thead><tbody><tr>" +
            "<td><textarea readonly>%s</textarea></td><td><textarea readonly>%s</textarea></td></tr></tbody></table>";

    @Given("web service endpoint '$webServiceEndpointUrl' and web service soap action '$webServiceSoapAction'")
    public void webServiceEndpointAndWebServiceSoapAction(String webServiceEndpointUrl, String webServiceSoapAction) {
        Thucydides.getCurrentSession().put(WEB_SERVICE_ENDPOINT_URL_KEY, webServiceEndpointUrl);
        LOGGER.info("Saved web service endpoint is: " + webServiceEndpointUrl);
        Thucydides.getCurrentSession().put(WEB_SERVICE_SOAP_ACTION_KEY, webServiceSoapAction);
        LOGGER.info("Saved web service soap action is: " + webServiceSoapAction);
    }

    @Given("published service endpoint '$serviceSuffix' with port '$port' and protocol '$protocol'")
    public void givenWebServiceEndpointWith(String serviceSuffix, String port, String protocol) throws MalformedURLException {
        String baseUrl = WebServiceProperties.getWebServiceProperty().getProperty("webdriver.base.url");
        LOGGER.info("published service endpoint, serviceSuffix " + serviceSuffix + " port " + port + " protocol " + protocol);
        savePublishedEndpoint(protocol, baseUrl, port, serviceSuffix);
    }

    @Given("web service SOAP Action '$webServiceSoapAction'")
    public void givenWebServiceURL(String webServiceSoapAction) {
        Thucydides.getCurrentSession().put(WEB_SERVICE_SOAP_ACTION_KEY, webServiceSoapAction);
        LOGGER.info("WEB_SERVICE_SOAP_ACTION_KEY " + webServiceSoapAction);
    }

    @When("send SOAP msg from file '$filePath'")
    public void sendSoapMsgFromFile(String filePath) {
        try {
            if (filePath.endsWith(".ftl")) {
                File fileOutput = prepareFreemarkerTemplate(filePath);
                LOGGER.info("prepareFreemarkerTemplate named " + fileOutput.getName());
                sendSoapMessage("/" + fileOutput.getName());
                LOGGER.info("sendSoapMessage with template was successfully");
            } else {
                sendSoapMessage(filePath);
                LOGGER.info("sendSoapMessage without template was successfully");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Then("get value from SOAP response by '$xpath' and save to '$variable'")
    public void getXPathFromLastResponse(String xPath, String variable) throws IOException, SOAPException, ParserConfigurationException, SAXException, XPathExpressionException {
        SOAPMessage rawSoapResponse = (SOAPMessage) Thucydides.getCurrentSession().get(RESPONSE);
        String cleanResponse = getXmlPayload(rawSoapResponse);
        InputSource source = new InputSource(new StringReader(cleanResponse));
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(source);
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression expr = xpath.compile(xPath);
        String result = (String) expr.evaluate(document, XPathConstants.STRING);
        Thucydides.getCurrentSession().put(variable, result);
        LOGGER.debug("result " + result);
    }

    @Then("verify that response is equal to file '$filePath'")
    public void verifyThatResponseIsEqualToFile(String filePath) throws SOAPException, IOException, SAXException {
        FileReader expectedResponseXML = new FileReader(getFileFromResourcesByFilePath(filePath));
        SOAPMessage rawSoapResponse = (SOAPMessage) Thucydides.getCurrentSession().get(RESPONSE);
        String cleanResponse = getXmlPayload(rawSoapResponse);
        StringReader actualResponseXML = new StringReader(cleanResponse);
        XMLUnit.setIgnoreWhitespace(true);
        Diff diff = new Diff(expectedResponseXML, actualResponseXML);
        StringBuffer stringBuffer = diff.appendMessage(new StringBuffer());
        assertTrue(stringBuffer.toString(), diff.similar());
    }

    @Step
    public void requestResponseDetails(String details) {
        //Just to show details in html report
    }

    @Then("verify that response is similar to file '$filePath'")
    public void verifyThatResponseIsSimilarToFile(String filePath) throws SOAPException, IOException, SAXException {
        FileReader expectedResponseXML = new FileReader(getFileFromResourcesByFilePath(filePath));
        SOAPMessage rawSoapResponse = (SOAPMessage) Thucydides.getCurrentSession().get(RESPONSE);
        String cleanResponse = getXmlPayload(rawSoapResponse);
        StringReader actualResponseXML = new StringReader(cleanResponse);
        Diff diff = new Diff(expectedResponseXML, actualResponseXML);
        diff.overrideDifferenceListener(new IgnoreTextAndAttributeValuesDifferenceListener());
        StringBuffer stringBuffer = diff.appendMessage(new StringBuffer());
        assertTrue(stringBuffer.toString(), diff.similar());
    }

    @Then("verify that '$faultType' is equal to '$value'")
    public void verifyThatFaultCodeIsEqualTo(String faultType, String value) throws SOAPException {
        SOAPMessage lastResponse = (SOAPMessage) Thucydides.getCurrentSession().get(RESPONSE);
        SOAPFault fault = lastResponse.getSOAPBody().getFault();
        if (FAULT_CODE.equalsIgnoreCase(faultType)) {
            assertEquals(value, fault.getFaultCode());
        } else if (FAULT_ACTOR.equalsIgnoreCase(faultType)) {
            assertEquals(value, fault.getFaultActor());
        } else if (FAULT_STRING.equalsIgnoreCase(faultType)) {
            assertEquals(value, fault.getFaultString());
        } else {
            throw new AssertionError("Please enter valid fault type such as: "
                    + FAULT_CODE + ", " + FAULT_ACTOR + ", " + FAULT_STRING);
        }
    }

    private String getXmlPayload(SOAPMessage rawSoapResponse) throws IOException, SOAPException {
        String prefix = rawSoapResponse.getSOAPPart().getEnvelope().getPrefix();
        String rawResponse = convertToString(rawSoapResponse);
        String xmlMessage = "";
        String beginSoapTag = "<" + prefix + ":Envelope";
        String endSoapTag = "</" + prefix + ":Envelope>";
        int start = rawResponse.indexOf(beginSoapTag);
        int end = rawResponse.lastIndexOf(endSoapTag) + prefix.length() + LENGTH_XML_ENCLOSING_TAG;
        if (start >= 0 || end > 0) {
            xmlMessage = rawResponse.substring(start, end);
            LOGGER.debug("xmlMessage = " + xmlMessage);
        } else {
            throw new IllegalArgumentException("Can't parse soap message " + rawResponse);
        }
        return xmlMessage;
    }

    public void savePublishedEndpoint(String protocol, String host, String port, String serviceSuffix) throws MalformedURLException {
        URL hostUrl = new URL(host);
        String endpointProtocol = HTTP_PROTOCOL;
        if (HTTPS_PROTOCOL.equalsIgnoreCase(protocol)) {
            endpointProtocol = HTTPS_PROTOCOL;
        }
        String webServiceEndpointUrl = endpointProtocol + "://" + hostUrl.getHost() + ":" + port + "/" + serviceSuffix;
        Thucydides.getCurrentSession().put(WEB_SERVICE_ENDPOINT_URL_KEY, webServiceEndpointUrl);
        LOGGER.info("Saved published endpoint URL is:" + webServiceEndpointUrl);
    }

    private void sendSoapMessage(String filePath) throws SOAPException, NoSuchAlgorithmException, KeyManagementException, IOException, TransformerException {
        Map<Object, Object> currentSession = Thucydides.getCurrentSession();
        String webServicesEndpointUrl = (String) currentSession.get(WEB_SERVICE_ENDPOINT_URL_KEY);
        String webServiceSoapAction = (String) currentSession.get(WEB_SERVICE_SOAP_ACTION_KEY);
        SOAPMessage soapRequest = createSOAPMessageFromFile(filePath, webServiceSoapAction);
        SOAPMessage rawSoapResponse = callWebServiceMethod(soapRequest, webServicesEndpointUrl);
        LOGGER.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>START SOAP REQUEST>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        LOGGER.debug(convertToString(soapRequest));
        LOGGER.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<END SOAP REQUEST<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        LOGGER.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>START SOAP RESPONSE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        LOGGER.debug(convertToString(rawSoapResponse));
        LOGGER.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<END SOAP RESPONSE<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        Thucydides.getCurrentSession().put(RESPONSE, rawSoapResponse);
        requestResponseDetails(String.format(TABLE_2_COLUMNS, "Actual SOAP Request", "Actual SOAP Response",
                convertToString(soapRequest), convertToString(rawSoapResponse)));
    }

    private File prepareFreemarkerTemplate(String filePath) throws IOException, TemplateException {
        File template = getFileFromResourcesByFilePath(filePath);
        Configuration config = new Configuration();
        config.setDirectoryForTemplateLoading(template.getParentFile());
        config.setObjectWrapper(new DefaultObjectWrapper());
        config.setDefaultEncoding("UTF-8");
        Template temp = config.getTemplate(template.getName());
        String child = template.getName() + RandomStringUtils.randomAlphanumeric(8);
        File fileOutput = new File(template.getParentFile(), child);
        Writer fileWriter = new FileWriter(fileOutput);
        Map<Object, Object> currentSession = Thucydides.getCurrentSession();
        temp.process(currentSession, fileWriter);
        return fileOutput;
    }

}

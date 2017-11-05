package io.tapack.satisfy.steps.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.soap.*;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import static io.tapack.util.FileUtils.getStreamFromResourcesByFilePath;
import static io.tapack.util.SSLUtils.doTrustToCertificates;

public final class WebServiceHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceHelper.class);
    private static final String SOAP_ACTION_PROPERTY_NAME = "SOAPAction";

    private WebServiceHelper() {
    }

    public static SOAPMessage callWebServiceMethod(SOAPMessage request, Object address) throws SOAPException, KeyManagementException, NoSuchAlgorithmException {
        doTrustToCertificates();
        SOAPConnectionFactory connectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = connectionFactory.createConnection();
        SOAPMessage response = connection.call(request, address);
        connection.close();
        return response;
    }

    public static void parseFault(SOAPMessage message) throws SOAPException {
        SOAPFault fault = message.getSOAPBody().getFault();
        LOGGER.info("fault code = " + fault.getFaultCode());
        LOGGER.info("fault actor = " + fault.getFaultActor());
        LOGGER.info("fault string = " + fault.getFaultString());
        LOGGER.info("detail = " + fault.getDetail());
    }

    public static SOAPMessage createSOAPMessageFromFile(String file, String webServiceSoapAction) throws SOAPException, FileNotFoundException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage message = messageFactory.createMessage();
        SOAPPart soapPart = message.getSOAPPart();
        MimeHeaders headers = message.getMimeHeaders();
        headers.addHeader(SOAP_ACTION_PROPERTY_NAME, webServiceSoapAction);
        StreamSource preppedMsgSrc = new StreamSource(getStreamFromResourcesByFilePath(file));
        soapPart.setContent(preppedMsgSrc);
        message.saveChanges();
        return message;
    }

    public static String convertToString(SOAPMessage message) throws SOAPException, IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        message.writeTo(os);
        String encodingString = "UTF-8";
        Object encoding = message.getProperty(SOAPMessage.CHARACTER_SET_ENCODING);
        if (encoding instanceof String) {
            encodingString = (String) encoding;
        }
        return new String(os.toByteArray(), encodingString);
    }
}
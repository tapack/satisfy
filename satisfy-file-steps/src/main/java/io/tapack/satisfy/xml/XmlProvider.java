package io.tapack.satisfy.xml;

import io.tapack.satisfy.spi.FileHandler;
import io.tapack.satisfy.spi.LoadAcceptor;
import org.apache.commons.io.FileUtils;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.IgnoreTextAndAttributeValuesDifferenceListener;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static io.tapack.util.FileUtils.getFileFromResourcesByFilePath;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

public class XmlProvider implements FileHandler, LoadAcceptor {

    @Override
    public boolean accept(Class<? extends LoadAcceptor> loadclass) {
        return this.getClass().equals(loadclass);
    }

    @Override
    public void checkFileContainText(String textContent, File lastDownloadedFile) throws IOException {
        Assert.assertThat("THE FILE MUST CONTAIN MESSAGE '" + textContent + "' BUT IT ISN'T ", FileUtils.readFileToString(lastDownloadedFile), containsString(textContent));
    }

    @Override
    public void checkFileDoesNotContainText(String textContent, File lastDownloadedFile) throws IOException {
        Assert.assertThat("THE FILE '" + lastDownloadedFile.getName() + "' SHOULD NOT CONTAIN TEXT '" + textContent + "', BUT IT CONTAIN", FileUtils.readFileToString(lastDownloadedFile), not(containsString(textContent)));
    }

    @Override
    public void verifyFilesOnEqual(String filePath, File lastDownloadedFile) throws IOException, SAXException, ParserConfigurationException {
        FileReader controlXML = new FileReader(getFileFromResourcesByFilePath(filePath));
        FileReader testXML = new FileReader(lastDownloadedFile);
        XMLUnit.setIgnoreWhitespace(true);
        XMLAssert.assertXMLEqual("THE FILE '" + lastDownloadedFile.getName() + "' DOESN'T CONTAIN EXPECTED VALUE", controlXML, testXML);
    }

    @Override
    public void verifyFileIsSimilarToFile(String filePath, File lastDownloadedFile) throws IOException, SAXException, ParserConfigurationException {
        FileReader controlXML = new FileReader(getFileFromResourcesByFilePath(filePath));
        FileReader testXML = new FileReader(lastDownloadedFile);
        Diff diff = new Diff(controlXML, testXML);
        diff.overrideDifferenceListener(new IgnoreTextAndAttributeValuesDifferenceListener());
        XMLAssert.assertXMLEqual(diff, true);
    }

    @Override
    public void verifyThatFileHasOccurrencesOfText(int count, String textContent, File lastDownloadedFile) throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc = factory.newDocumentBuilder().parse(lastDownloadedFile);
        NodeList nodes = doc.getElementsByTagName(textContent);
        int actual = nodes.getLength();
        XMLAssert.assertEquals(count, actual);
    }

    //EMPTY STUB
    @Override
    public void verifyThatFileTextContentIsEqualToFileTextContent(String filePath, File lastDownloadedFile) throws IOException, ParserConfigurationException, SAXException {
        verifyFileIsSimilarToFile(filePath, lastDownloadedFile);
    }


}

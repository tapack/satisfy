package io.tapack.satisfy.steps.bdd;

import io.tapack.satisfy.spi.FileHandler;
import io.tapack.satisfy.spi.FileStepFactory;
import io.tapack.satisfy.xml.XmlProvider;
import org.jbehave.core.annotations.Then;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static io.tapack.satisfy.util.DownloadHelper.getDownloadedFile;

public class XmlSteps {

public FileHandler steps = FileStepFactory.getXmlSteps(XmlProvider.class);

    @Then("verify XML file content is equal to file '$filePath' with '$key'")
    public void verifyXMLIsEqualToFile(String filePath, String key) throws IOException, SAXException, ParserConfigurationException {
        steps.verifyFilesOnEqual(filePath, getDownloadedFile(key));
    }

    @Then("verify XML file content is similar to file '$filePath' with '$key'")
    public void verifyXMLIsSimilarToFile(String filePath, String key) throws IOException, SAXException, ParserConfigurationException {
        steps.verifyFileIsSimilarToFile(filePath, getDownloadedFile(key));
    }

    @Then("verify XML file contains text '$textContent' with '$key'")
    public void verifyXMLContainText(String textContent, String key) throws IOException {
        steps.checkFileContainText(textContent, getDownloadedFile(key));
    }

    @Then("verify XML file does not contain text '$textContent' with '$key'")
    public void verifyXMLDoesNotContainText(String textContent, String key) throws IOException {
        steps.checkFileDoesNotContainText(textContent, getDownloadedFile(key));
    }
}

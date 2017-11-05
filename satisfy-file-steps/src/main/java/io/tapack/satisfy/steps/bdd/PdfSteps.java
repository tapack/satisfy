package io.tapack.satisfy.steps.bdd;

import io.tapack.satisfy.pdf.PdfProvider;
import io.tapack.satisfy.spi.FileHandler;
import io.tapack.satisfy.spi.FileStepFactory;
import org.jbehave.core.annotations.Then;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static io.tapack.satisfy.util.DownloadHelper.getDownloadedFile;

public class PdfSteps {

    public FileHandler steps = FileStepFactory.getPdfSteps(PdfProvider.class);

    @Then("verify that pdf file contains text '$textContent' with '$key'")
    public void thenVerifyThatPdfFileContainsText(String textContent, String key) throws IOException {
        steps.checkFileContainText(textContent, getDownloadedFile(key));
    }

    @Then("verify that pdf file does not contains text '$textContent' with '$key'")
    public void thenVerifyPdfFileDoesNotContainText(String textContent, String key) throws IOException {
        steps.checkFileDoesNotContainText(textContent, getDownloadedFile(key));
    }

    @Then("verify that pdf file has '$count' occurrences of text '$textContent' with '$key'")
    public void thenVerifyThatPdfFileHasOccurrencesOfText(int count, String textContent, String key) throws IOException, ParserConfigurationException, SAXException {
        steps.verifyThatFileHasOccurrencesOfText(count, textContent, getDownloadedFile(key));
    }

    @Then("verify that pdf text content is equal to '$filePath' text content in file with key '$key'")
    public void thenVerifyThatPdfTextContentIsEqualToFileTextContent(String filePath, String key) throws IOException, ParserConfigurationException, SAXException {
        steps.verifyThatFileTextContentIsEqualToFileTextContent(filePath, getDownloadedFile(key));
    }
}

package io.tapack.satisfy.steps.bdd;

import io.tapack.satisfy.csv.CsvProvider;
import io.tapack.satisfy.spi.FileHandler;
import io.tapack.satisfy.spi.FileStepFactory;
import io.tapack.satisfy.util.TableToCsv;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.model.ExamplesTable;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static io.tapack.satisfy.util.DownloadHelper.getDownloadedFile;
import static io.tapack.util.SessionVariablesUtils.save;

public class CsvSteps {

    public FileHandler steps = FileStepFactory.getCsvSteps(CsvProvider.class);
    public TableToCsv tableToCsv = new TableToCsv();

    @Given("csv file '$fileName' with content: $content")
    public void givenCsvFileWithContentFromTable(String fileName, ExamplesTable content) throws IOException {
        save(fileName, tableToCsv.create(fileName, content));
    }

    @Then("verify that csv file contains text '$textContent' with '$key'")
    public void thenVerifyThatCsvFileContainsText(String textContent, String key) throws IOException {
        steps.checkFileContainText(textContent, getDownloadedFile(key));
    }

    @Then("verify that csv file does not contains text '$textContent' with '$key'")
    public void thenVerifyCsvFileDoesNotContainText(String textContent, String key) throws IOException {
        steps.checkFileDoesNotContainText(textContent, getDownloadedFile(key));
    }

    @Then("verify that csv text content is equal to '$filePath' text content in file with key '$key'")
    public void thenVerifyThatCsvTextContentIsEqualToFileTextContent(String filePath, String key) throws IOException, ParserConfigurationException, SAXException {
        steps.verifyThatFileTextContentIsEqualToFileTextContent(filePath, getDownloadedFile(key));
    }
}

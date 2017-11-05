package io.tapack.satisfy.csv;

import io.tapack.satisfy.spi.FileHandler;
import io.tapack.satisfy.spi.LoadAcceptor;
import org.junit.Assert;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static io.tapack.util.FileUtils.getFileFromResourcesByFilePath;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.apache.commons.io.FileUtils.readLines;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

public class CsvProvider implements FileHandler, LoadAcceptor {

    @Override
    public boolean accept(Class<? extends LoadAcceptor> loadclass) {
        return this.getClass().equals(loadclass);
    }

    @Override
    public void checkFileContainText(String textContent, File lastDownloadedFile) throws IOException {
        Assert.assertThat("THE FILE MUST CONTAIN MESSAGE '" + textContent + "' BUT IT ISN'T ", readFileToString(lastDownloadedFile), containsString(textContent));
    }

    @Override
    public void checkFileDoesNotContainText(String textContent, File lastDownloadedFile) throws IOException {
        Assert.assertThat("THE FILE '" + lastDownloadedFile.getName() + "' SHOULD NOT CONTAIN TEXT '" + textContent + "', BUT IT CONTAIN", readFileToString(lastDownloadedFile), not(containsString(textContent)));
    }

    @Override
    public void verifyThatFileTextContentIsEqualToFileTextContent(String filePath, File lastDownloadedFile) throws IOException, ParserConfigurationException, SAXException {
        List<String> expectedCsv = readLines(getFileFromResourcesByFilePath(filePath));
        List<String> actualCsv = readLines(lastDownloadedFile);
        Assert.assertEquals("THE FILE '" + lastDownloadedFile.getName() + "' DOESN'T CONTAIN EXPECTED VALUE", expectedCsv, actualCsv);
    }

    @Override
    public void verifyFilesOnEqual(String filePath, File lastDownloadedFile) throws IOException, SAXException, ParserConfigurationException {
        throw new UnsupportedOperationException("verifyFilesOnEqual doesn't support for csv");
    }

    @Override
    public void verifyThatFileHasOccurrencesOfText(int count, String textContent, File lastDownloadedFile) throws IOException, ParserConfigurationException, SAXException {
        throw new UnsupportedOperationException("verifyThatFileHasOccurrencesOfText doesn't support for csv");
    }

    @Override
    public void verifyFileIsSimilarToFile(String filePath, File lastDownloadedFile) throws IOException, SAXException, ParserConfigurationException {
        throw new UnsupportedOperationException("verifyFileIsSimilarToFile doesn't support for csv");
    }
}

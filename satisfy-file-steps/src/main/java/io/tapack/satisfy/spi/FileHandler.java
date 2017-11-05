package io.tapack.satisfy.spi;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public interface FileHandler extends LoadAcceptor {

    public void checkFileContainText(String textContent, File lastDownloadedFile) throws IOException;

    public void checkFileDoesNotContainText(String textContent, File lastDownloadedFile) throws IOException;

    public void verifyFilesOnEqual(String filePath, File lastDownloadedFile) throws IOException, SAXException, ParserConfigurationException;

    public void verifyThatFileHasOccurrencesOfText(int count, String textContent, File lastDownloadedFile) throws IOException, ParserConfigurationException, SAXException;

    public void verifyThatFileTextContentIsEqualToFileTextContent(String filePath, File lastDownloadedFile) throws IOException, ParserConfigurationException, SAXException;

    public void verifyFileIsSimilarToFile(String filePath, File lastDownloadedFile) throws IOException, SAXException, ParserConfigurationException;


}

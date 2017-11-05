package io.tapack.satisfy.pdf;

import io.tapack.satisfy.spi.FileHandler;
import io.tapack.satisfy.spi.LoadAcceptor;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


public class PdfProvider implements FileHandler, LoadAcceptor {

    @Override
    public boolean accept(Class<? extends LoadAcceptor> loadclass) {
        return this.getClass().equals(loadclass);
    }

    @Override
    public void checkFileContainText(String textContent, File lastDownloadedFile) throws IOException {
        PDFWords pdfLibrary = new PDFWords();
        pdfLibrary.parsePdf(lastDownloadedFile.getPath());
        pdfLibrary.pdfShouldContain(textContent);
    }

    @Override
    public void checkFileDoesNotContainText(String textContent, File lastDownloadedFile) throws IOException {
        PDFWords pdfLibrary = new PDFWords();
        pdfLibrary.parsePdf(lastDownloadedFile.getPath());
        pdfLibrary.pdfShouldNotContain(textContent);
    }

    @Override
    public void verifyThatFileHasOccurrencesOfText(int count, String textContent, File lastDownloadedFile) throws IOException {
        PDFWords pdfLibrary = new PDFWords();
        pdfLibrary.parsePdf(lastDownloadedFile.getPath());
        pdfLibrary.pdfShouldContainOccurrencesOfString(textContent, count);
    }

    @Override
    public void verifyThatFileTextContentIsEqualToFileTextContent(String filePath, File lastDownloadedFile) throws IOException {
        PDFWords actualPDFcontent = new PDFWords();
        actualPDFcontent.parsePdf(lastDownloadedFile.getPath());
        PDFWords expectedPDFcontent = new PDFWords();
        expectedPDFcontent.parsePdf(filePath);
        actualPDFcontent.pdfShouldEqualTo(expectedPDFcontent);
    }

    @Override
    public void verifyFilesOnEqual(String filePath, File lastDownloadedFile) throws IOException, SAXException, ParserConfigurationException {
        Reader expectedFile = new FileReader(lastDownloadedFile);
        Reader actualFile = new FileReader(filePath);
        boolean isEquals = IOUtils.contentEquals(expectedFile, actualFile);
        Assert.assertTrue("File must be equal on 100%", isEquals);
    }


    @Override
    public void verifyFileIsSimilarToFile(String filePath, File lastDownloadedFile) throws IOException, SAXException, ParserConfigurationException {
        throw  new UnsupportedOperationException("verifyFileIsSimilarToFile doesn't support for pdf");
    }


}

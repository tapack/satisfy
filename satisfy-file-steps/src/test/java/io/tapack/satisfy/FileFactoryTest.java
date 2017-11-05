package io.tapack.satisfy;

import io.tapack.satisfy.csv.CsvProvider;
import io.tapack.satisfy.pdf.PdfProvider;
import io.tapack.satisfy.spi.FileHandler;
import io.tapack.satisfy.spi.FileStepFactory;
import io.tapack.satisfy.xml.XmlProvider;
import org.junit.Assert;
import org.junit.Test;

public class FileFactoryTest {

    @Test
    public void fileFactoryShouldReturnXmlProvider() {
        FileHandler xmlProvider = FileStepFactory.getXmlSteps(XmlProvider.class);
        Assert.assertEquals("xml provider must be equal with XmlProvider class", XmlProvider.class, xmlProvider.getClass());
    }

    @Test
    public void fileFactoryShouldReturnPdfProvider() {
        FileHandler pdfProvider = FileStepFactory.getPdfSteps(PdfProvider.class);
        Assert.assertEquals("pdf provider must be equal with PdfProvider class", PdfProvider.class, pdfProvider.getClass());
    }

    @Test
    public void fileFactoryShouldReturnCsvProvider() {
        FileHandler csvProvider = FileStepFactory.getCsvSteps(CsvProvider.class);
        Assert.assertEquals("csv provider must be equal with CsvProvider class", CsvProvider.class, csvProvider.getClass());
    }
}

package io.tapack.satisfy;

import io.tapack.satisfy.pdf.PDFWords;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class PDFWordsTest {

    PDFWords pdfWords;

    @Before
    public void setUp() throws Exception {
        pdfWords = new PDFWords();
    }

    @Test
    public void pdfShouldContainsTextTest() throws IOException {
        pdfWords.parsePdf("/static-test-example/data/cars.pdf");
        pdfWords.pdfShouldContain("BMW");
    }

    @Test
    public void pdfShouldNotContainsTextTest() throws IOException {
        pdfWords.parsePdf("/static-test-example/data/cars.pdf");
        pdfWords.pdfShouldNotContain("Oldsmobile");
    }

    @Test
    public void pdfShouldContainOccurrencesOfStringTest() throws IOException {
        pdfWords.parsePdf("/static-test-example/data/cars.pdf");
        pdfWords.pdfShouldContainOccurrencesOfString("Blue", 3);
    }

}
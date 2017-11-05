package io.tapack.satisfy.pdf;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PDFWords {

    private static final Logger LOGGER = LoggerFactory.getLogger(PDFWords.class);
    private Map<Integer, String> pdfData;

    public void pdfShouldEqualTo(PDFWords expectedPDFcontent) {
        assertThat(pdfData, equalTo(expectedPDFcontent.pdfData));
    }

    public void parsePdf(String filename) throws IOException {
        PdfReader reader = new PdfReader(filename);
        LOGGER.trace("Reading file " + filename);
        pdfData = new HashMap<Integer, String>();
        int numberOfPages = reader.getNumberOfPages();
        for (int page = 1; page <= numberOfPages; page++) {
            LOGGER.trace("Reading page " + page);
            String textFromPage = PdfTextExtractor.getTextFromPage(reader, page);
            pdfData.put(page, textFromPage);
        }
    }

    public void pdfShouldContainUnOrdered(PDFWords expectedPDFcontent) {
        Collection<String> values = pdfData.values();
        for (String value : values) {
            expectedPDFcontent.pdfShouldContain(value);
        }
    }

    public void pdfShouldContain(String expectedValue) {
        Collection<String> values = pdfData.values();
        collectionShouldContain(expectedValue, values);
    }

    public void pdfShouldNotContain(String expectedValue) {
        Collection<String> values = pdfData.values();
        collectionShouldNotContain(expectedValue, values);
    }

    public void pdfShouldContainOccurrencesOfString(String expectedValue, int expectedCount) {
        Collection<String> values = pdfData.values();
        Assert.assertEquals("THE MESSAGE '" + expectedValue + "' EXPECTED " + expectedCount + " BUT IN REALY = " + getCountMatchesInCollectiont(expectedValue, values), expectedCount, getCountMatchesInCollectiont(expectedValue, values));
    }

    public void pdfShouldContain(String expectedValue, String ignoreLinebreaks) {
        if (StringUtils.isEmpty(ignoreLinebreaks))
            pdfShouldContain(expectedValue);
        Collection<String> values = pdfData.values();
        values = removeLinebreaks(values);
        collectionShouldContain(expectedValue, values);
    }

    public void pdfShouldContain(String expectedValue, String ignoreLinebreaks, String ignoreCase) {
        if (StringUtils.isEmpty(ignoreCase))
            pdfShouldContain(expectedValue, ignoreLinebreaks);
        Collection<String> values = pdfData.values();
        values = removeLinebreaks(values);
        collectionShouldContain(expectedValue, values, StringUtils.isNotEmpty(ignoreCase));
    }

    protected Collection<String> removeLinebreaks(Collection<String> values) {
        List<String> contentWithoutLinebreacks = new ArrayList<String>();
        for (String string : values) {
            String reformattedString = string.replaceAll("\\n", " ");
            while (reformattedString.contains("  ")) {
                reformattedString = reformattedString.replaceAll("  ", " ");
            }
            contentWithoutLinebreacks.add(reformattedString);
        }
        return contentWithoutLinebreacks;
    }

    private boolean isContaining(String expectedValue, boolean ignoreCase, String content) {
        return ignoreCase ? StringUtils.containsIgnoreCase(content, expectedValue) : StringUtils.contains(content, expectedValue);
    }

    private void collectionShouldContain(String expectedValue, Collection<String> values) {
        collectionShouldContain(expectedValue, values, false);
    }

    private void collectionShouldContain(String expectedValue, Collection<String> values, boolean ignoreCase) {
        for (String content : values) {
            if (isContaining(expectedValue, ignoreCase, content)) {
                return;
            }
        }
        throw new AssertionError("COULD NOT FIND " + expectedValue + " IN " + pdfData);
    }

    private void collectionShouldNotContain(String expectedValue, Collection<String> values, boolean ignoreCase) {
        for (String content : values) {
            if (isContaining(expectedValue, ignoreCase, content)) {
                throw new AssertionError("TEXT '" + expectedValue + "' CONTAINS IN " + pdfData);
            }
        }
    }

    private int getCountMatchesInCollectiont(String expectedValue, Collection<String> values) {
        int count = 0;
        for (String content : values) {
            count += StringUtils.countMatches(content, expectedValue);
        }
        return count;
    }

    private void collectionShouldNotContain(String expectedValue, Collection<String> values) {
        collectionShouldNotContain(expectedValue, values, false);
    }
}

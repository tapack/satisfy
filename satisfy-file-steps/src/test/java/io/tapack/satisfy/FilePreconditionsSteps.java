package io.tapack.satisfy;

import net.thucydides.core.Thucydides;
import org.jbehave.core.annotations.When;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class FilePreconditionsSteps {

    @When("the user load '$path' file with  key '$key'")
    public void loadLocalPDFFile(String path, String key) throws URISyntaxException {
        URL pdfFile = FilePreconditionsSteps.class.getResource(path);
        try {
            Object content = pdfFile.getContent();
            URI uri = pdfFile.toURI();
            File file = new File(uri);
            Thucydides.getCurrentSession().put(key, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

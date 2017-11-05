package io.tapack.satisfy.steps.bdd;

import io.tapack.satisfy.steps.spi.WebStepsFactory;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;

public class FileUploadBddSteps {

    @When("user upload '$filePathOrKey' to field '$fileInput'")
    public void uploadFileToElementFromLocalResources(String file, By id) {
        WebStepsFactory.getUploadSteps(id).uploadFileToFileInput(file);
    }

}
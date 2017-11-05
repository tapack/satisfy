package io.tapack.satisfy.steps.bdd;

import io.tapack.satisfy.steps.spi.WebStepsFactory;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;

public class FileDownloadBddSteps {

    @When("user downloads file by click on '$identity' and save as '$fileName'")
    public void downloadFile(By identity, String fileName) {
        WebStepsFactory.getDownloadSteps(identity).downloadFile(fileName);
    }

}
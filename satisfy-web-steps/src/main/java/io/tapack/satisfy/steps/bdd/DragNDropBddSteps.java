package io.tapack.satisfy.steps.bdd;

import io.tapack.satisfy.steps.spi.WebStepsFactory;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;

public class DragNDropBddSteps {

    @When("select '$element' and move with offset '$x', '$y' coordinate")
    public void whenSelectElementByXpathAndMoveTo(By identity, int x, int y) {
        WebStepsFactory.getDragNDropSteps(identity)
                .whenSelectElementAndMoveTo(x, y);
    }

    @Then("verify element '$identity' has correct coordinate '$x', '$y'")
    public void thenVerifyElementHasCorrectCoordinate(By identity, int x, int
            y) {
        WebStepsFactory.getDragNDropSteps(identity)
                .thenCheckElementHasCorrectCoordinate(x, y);
    }

}

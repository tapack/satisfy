package io.tapack.satisfy.steps.bdd;

import io.tapack.satisfy.steps.spi.WebStepsFactory;
import org.jbehave.core.annotations.Then;
import org.openqa.selenium.By;

public class WebElementWaitSteps {

    @Then("wait '$identity' is visible")
    public void thenWaitIdentityIsDisplayed(By identity) {
        WebStepsFactory.getWaitSteps().waitIdentityIsDisplayed(identity);
    }

    @Then("wait '$identity' is not visible")
    public void thenWaitIdentityIsNotDisplayed(By identity) {
        WebStepsFactory.getWaitSteps().waitIdentityIsNotDisplayed(identity);
    }

}

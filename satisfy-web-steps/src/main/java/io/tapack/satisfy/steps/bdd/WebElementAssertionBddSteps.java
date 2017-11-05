package io.tapack.satisfy.steps.bdd;

import io.tapack.satisfy.steps.spi.WebStepsFactory;
import org.jbehave.core.annotations.Then;
import org.openqa.selenium.By;

public class WebElementAssertionBddSteps {

    @Then("'$identity' contains '$text' text")
    public void thenIdentityContainsText(By identity, String text) {
        WebStepsFactory.getAssertionSteps(identity).identityContainsText(text);
    }

    @Then("'$identity' does not contain '$text' text")
    public void thenIdentityDoesNotContainText(By identity, String text) {
        WebStepsFactory.getAssertionSteps(identity)
                .identityDoesNotContainText(text);
    }

}

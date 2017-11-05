package io.tapack.satisfy.steps.bdd;

import io.tapack.satisfy.steps.spi.WebStepsFactory;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;

public class RichEditorBddSteps {

    @When("fill '$identity' rich editor with '$text' text")
    public void whenFillRichEditorWithText(By identity, String text) {
        WebStepsFactory.getRichEditorSteps(identity).fillEditorWithText(text);
    }

    @Then("'$identity' rich editor contains '$text' text")
    public void thenEditorContainsText(By identity, String text) {
        WebStepsFactory.getRichEditorSteps(identity).verifyEditorContainsText
                (text);
    }

    @Then("'$identity' rich editor does not contains '$text' text")
    public void thenEditorNotContainsText(By identity, String text) {
        WebStepsFactory.getRichEditorSteps(identity)
                .verifyEditorNotContainsText(text);
    }

}
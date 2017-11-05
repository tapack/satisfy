package io.tapack.satisfy.steps.bdd;

import io.tapack.satisfy.steps.spi.WebStepsFactory;
import org.jbehave.core.annotations.AsParameterConverter;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;

import static io.tapack.util.SessionVariablesUtils.localize;
import static io.tapack.util.SessionVariablesUtils.substituteVariableByValue;

public class WebElementActionsBddSteps {

    @AsParameterConverter
    public By convertToSelector(String identity) {
        return WebStepsFactory.getPage()
                .convertToSelector(
                        localize(substituteVariableByValue(identity)));
    }

    @When("click '$identity'")
    public void whenClicksOnElement(By identity) {
        WebStepsFactory.getWebElementSteps(identity).whenClicksOnElement();
    }

    @When("click element with text '$text'")
    public void whenClicksOnElementWithText(String text) {
        String s = String.format("//*[contains(text(),'%1$s') or contains(@value,'%1$s')]", text);
        WebStepsFactory.getWebElementSteps(By.xpath(s)).whenClicksOnElement();
    }

    @When("fill '$identity' with '$value'")
    public void fillField(By identity, String value) {
        WebStepsFactory.getWebElementSteps(identity).fillField(value);
    }

    @Then("element '$identity' contains attribute '$attributeName' with value '$attributeValue'")
    public void thenElementContainsAttribute(By identity, String
            attributeName, String attributeValue) {
        WebStepsFactory.getWebElementSteps(identity)
                .thenElementContainsAttributeWithValue(attributeName,
                        attributeValue);
    }

    @Then("element '$identity' does not contain attribute '$attributeName' with value '$attributeValue'")
    public void thenElementDoesNotContainAttribute(By identity, String
            attributeName, String attributeValue) {
        WebStepsFactory.getWebElementSteps(identity)
                .thenElementDoesNotContainAttributeWithValue(attributeName,
                        attributeValue);
    }

}

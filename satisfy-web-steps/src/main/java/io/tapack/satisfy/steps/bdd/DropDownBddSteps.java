package io.tapack.satisfy.steps.bdd;

import io.tapack.satisfy.steps.spi.WebStepsFactory;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;

public class DropDownBddSteps {

    @When("select '$option' from '$identity' drop-down")
    public void whenSelectOptionFromDropDown(String option, By identity) {
        WebStepsFactory.getDropDownSteps(identity)
                .whenSelectOptionFromDropDown(option);
    }

    @Then("'$option' is selected from '$identity' drop-down")
    public void thenOptionIsSelectedFromDropDown(String option, By identity) {
        WebStepsFactory.getDropDownSteps(identity)
                .thenOptionIsSelectedFromDropDown(option);
    }

}
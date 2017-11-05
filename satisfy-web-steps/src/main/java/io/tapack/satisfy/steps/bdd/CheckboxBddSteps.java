package io.tapack.satisfy.steps.bdd;

import io.tapack.satisfy.steps.spi.WebStepsFactory;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;

public class CheckboxBddSteps {

    @When("make checkbox '$identity' checked")
    public void whenMakeCheckboxChecked(By identity) {
        WebStepsFactory.getCheckboxSteps(identity).whenMakeCheckboxChecked();
    }

    @When("make checkbox '$identity' unchecked")
    public void whenMakeCheckboxUnchecked(By identity) {
        WebStepsFactory.getCheckboxSteps(identity).whenMakeCheckboxUnchecked();
    }

}

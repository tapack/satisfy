package io.tapack.satisfy.steps.bdd;

import io.tapack.satisfy.steps.spi.WebStepsFactory;
import org.jbehave.core.annotations.When;

public class RadioButtonBddSteps {

    @When("click radio button with label '$option'")
    public void whenClickRadioButtonWithLabel(String option) {
        WebStepsFactory.getRadioButtonSteps().whenClickRadioButtonWithLabel
                (option);
    }

}

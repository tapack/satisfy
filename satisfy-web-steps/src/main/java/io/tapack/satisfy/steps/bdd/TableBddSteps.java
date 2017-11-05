package io.tapack.satisfy.steps.bdd;

import io.tapack.satisfy.steps.spi.WebStepsFactory;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.openqa.selenium.By;

public class TableBddSteps {

    @When("in '$identity' table click on '$element' for row with parameters: $params")
    public void whenInTableClickElementForRowWithParameters(By identity,
                                                            By element,
                                                            ExamplesTable
                                                                    params) {
        WebStepsFactory.getTableSteps(identity)
                .inTableClickOnIdentityInRowsWithParameters(element, params);
    }

    @Then("verify that in table '$identity' present row where: $params")
    public void verifyThatInTableIsPresentRowRowWithParameters(By identity,
                                                               ExamplesTable
                                                                       params) {
        WebStepsFactory.getTableSteps(identity)
                .tableContainsRowWithParameters(params);
    }

    @Then("verify that in table '$identity' is absent row where: $params")
    public void verifyThatInTableIsAbsentRowWithParameters(By identity,
                                                           ExamplesTable
                                                                   params) {
        WebStepsFactory.getTableSteps(identity)
                .tableDoesNotContainRowWithParameters(params);
    }

}

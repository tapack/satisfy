package io.tapack.satisfy.steps.bdd;

import io.tapack.satisfy.steps.spi.WebStepsFactory;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;

import java.util.List;

public class PicklistBddSteps {

    @When("select '$items' from '$identity' picklist")
    public void whenSelectFromPicklist(List<String> items, By identity) {
        WebStepsFactory.getPicklistSteps(identity).whenSelectFromPicklist
                (items);
    }

    @When("double click on item '$item' from '$identity' picklist")
    public void whenDoubleClickOnItemFromPicklist(String item, By identity) {
        WebStepsFactory.getPicklistSteps(identity)
                .whenDoubleClickOnItemFromPicklist(item);
    }

    @When("press add button in '$identity' picklist")
    public void whenPressAddButtonInPicklist(By identity) {
        WebStepsFactory.getPicklistSteps(identity)
                .whenPressAddButtonInPicklist();
    }

    @When("press add all button in '$identity' picklist")
    public void whenPressAddAllButtonInPicklist(By identity) {
        WebStepsFactory.getPicklistSteps(identity)
                .whenPressAddAllButtonInPicklist();
    }

    @When("press remove button in '$identity' picklist")
    public void whenPressRemoveButtonInPicklist(By identity) {
        WebStepsFactory.getPicklistSteps(identity)
                .whenPressRemoveButtonInPicklist();
    }

    @When("press remove all button in '$identity' picklist")
    public void whenPressRemoveAllButtonInPicklist(By identity) {
        WebStepsFactory.getPicklistSteps(identity)
                .whenPressRemoveAllButtonInPicklist();
    }

    @Then("'$items' appeared in '$identity' picklist source list")
    public void thenAppearedInPicklistSourceList(List<String> items, By
            identity) {
        WebStepsFactory.getPicklistSteps(identity)
                .thenAppearedInPicklistSourceList(items);
    }

    @Then("'$items' appeared in '$identity' picklist target list")
    public void thenAppearedInPicklistTargetList(List<String> items, By
            identity) {
        WebStepsFactory.getPicklistSteps(identity)
                .thenAppearedInPicklistTargetList(items);
    }

}

package io.tapack.satisfy.steps.bdd;

import io.tapack.satisfy.steps.spi.PageSteps;
import io.tapack.satisfy.steps.spi.WebStepsFactory;
import net.thucydides.core.Thucydides;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;

public class PageBddSteps {

    private PageSteps steps = WebStepsFactory.getPageSteps();

    @When("open '$path' page")
    public void open(String path) {
        steps.open(path);
    }

    @Then("page has '$title' title")
    public void pageHasTitle(String title) {
        steps.pageHasTitle(title);
    }

    @When("switch to new window")
    public void switchToNewWindow() {
        steps.switchToNewWindow();
    }

    @When("return to base window")
    public void returnToBaseWindow() {
        steps.returnToBaseWindow();
    }

    @When("switch to '$identity' iframe")
    public void switchToIFrame(By identity) {
        steps.switchToIFrame(identity);
    }

    @When("return to page from iframe")
    public void returnToPageFromIFrame() {
        steps.returnToPageFromIFrame();
    }

    @When("save text from '$identity' to '$name' variable")
    public void whenSaveTextToVariable(By identity, String name) {
        Thucydides.getCurrentSession().put(name, WebStepsFactory.getPage()
                .element(identity).getText());
    }
}